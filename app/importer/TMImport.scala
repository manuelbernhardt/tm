package importer

import collection.JavaConversions._
import javax.persistence.TypedQuery
import collection.mutable.Map
import models.tm.test.Tag
import models.tm.test.Tag.TagType
import models.tm._
import java.lang.{Long, String}
import play.db.jpa.{Model, JPA}
import tree.persistent.AbstractTree
import java.lang.reflect.{Method, Constructor}
import controllers.{Dashboard, RequirementTree}
import util.Logger

/**
 * TM import definitions and converters
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */

object TMImport {
  val ACCOUNT_ID: String = "accountId"
  val PROJECT: String = "project"

  val modelConverters = Map[String, ModelConverter[_]](
    classOf[TMUser].getName -> TMUserConverter,
    classOf[Tag].getName -> TagsConverter,
    classOf[ProjectTreeNode].getName -> ProjectTreeNodeConverter
  )

  val cellConverters = Map(
    classOf[Requirement].getName -> List(
      StringColumnImportRule(0, "name"),
      StringColumnImportRule(1, "description"),
      UserColumnImportRule(2, "createdBy"),
      TagsColumnImportRule(3, "tags"),
      ProjectTreeNodeColumnImportRule(4, null)
    ),
    classOf[Defect].getName -> List(
      StringColumnImportRule(0, "name"),
      StringColumnImportRule(1, "description"),
      StringColumnImportRule(2, "submittedBy")
    )
  )


}

trait TMConverter {
  def getProject(contextData: Map[String, AnyRef]) = {
    contextData.get(TMImport.PROJECT).get.asInstanceOf[Project]
  }
}

object TMUserConverter extends ModelConverter[TMUser] {
  def convert(data: AnyRef, entity: Class[_ <: Model], currentModelInstance: Model, contextData: Map[String, AnyRef]) = {

    // Name Surname => TMUser
    val query: TypedQuery[TMUser] = JPA.em().createQuery("from TMUser u where concat(concat(u.user.firstName, ' '), u.user.lastName) = :fullName and u.account.id = :accountId", classOf[TMUser])
    query.setParameter("fullName", data.asInstanceOf[String])
    query.setParameter("accountId", contextData.get(TMImport.ACCOUNT_ID).getOrElse(throw new ImportError("You need to provide the 'accountId' argument as context data")))
    val user: java.util.List[_] = query.getResultList
    user.length match {
      case 0 => throw new ImportError("No user with name '%s' found".format(data)) // no user found, we could create one...
      case 1 => user.get(0).asInstanceOf[TMUser]
      case _ => throw new ImportError("Too many users with name %s".format(data)) // TODO add import error instead of throwing it here
    }
  }

  def afterConvert(instance: Model, contextData: java.util.Map[String, AnyRef]) = {}
}

object TagsConverter extends ModelConverter[Tag] with TMConverter {
  def convert(data: AnyRef, baseModelType: Class[_ <: Model], currentModelInstance: Model, contextData: Map[String, AnyRef]) = {

    val tagType: TagType = TagType.getFromClass(baseModelType)

    val result: Array[Tag] = for (t <- data.asInstanceOf[String].split(',')) yield {
      val q: TypedQuery[Tag] = JPA.em().createQuery("from Tag t where t.name = :tagName and t.type = :tagType", classOf[Tag])
      q.setParameter("tagName", t.trim)
      q.setParameter("tagType", tagType)
      val tags: java.util.List[Tag] = q.getResultList
      val tagInstance: Tag = tags.length match {
        case 0 => {
          val tag: Tag = new Tag(getProject(contextData)) // TODO getOrElse programmer error
          tag.name = t.trim
          tag.`type` = tagType

          // TODO error handling
          tag.create

          tag
        }
        case 1 => tags.get(0)
        case _ => throw new ImportError("Too many tags with name %s and type %s".format(data, tagType.name)) // TODO add import error instead of throwing it here
      }
      tagInstance
    }

    asJavaList(result.toList)
  }

  def afterConvert(instance: Model, contextData: java.util.Map[String, AnyRef]) = {}
}

/**this converter is special in that it does not return anything but persists new nodes **/
object ProjectTreeNodeConverter extends ModelConverter[ProjectTreeNode] with TMConverter {

  val TREENODE_PREFIX = "treeNode_"
  val PREVIOUS_TREENODE = "previousTreeNode"

  val modelTreeMapping: Map[String, Class[_ <: AbstractTree]] = Map(
    classOf[Requirement].getName -> classOf[RequirementTree]
  )

  val modelFolderMapping: Map[String, Class[_ <: ProjectModel]] = Map(
    classOf[Requirement].getName -> classOf[RequirementFolder]
  )

  def getTreeId(c: Class[_ <: Model]) = {
    val name: String = modelTreeMapping.get(c.getName).get.getSimpleName
    name.substring(0, 1).toLowerCase() + name.substring(1)
  }

  def convert(data: AnyRef, baseModelType: Class[_ <: Model], currentModelInstance: Model, contextData: Map[String, AnyRef]) = {

    val path: String = data.asInstanceOf[String]
    val project: Project = getProject(contextData)
    val treeId = getTreeId(baseModelType)
    val nodeType = AbstractTree.getNodeType(baseModelType).getName

    val node: ProjectTreeNode = contextData.get(TREENODE_PREFIX + path.substring(0, path.lastIndexOf('/'))) match {
      // first, check if we already created or retrieved a parent path
      case Some(parent: ProjectTreeNode) => {
        val name: String = path.substring(path.lastIndexOf('/') + 1);
        val level: Int = path.split('/').length - 1

        // create the node, without nodeId yet
        createNode(name, level, parent, treeId, project, nodeType)
      }
      case None => {

        // look if there is already a parent node in the database
        val parents: java.util.List[_] = JPA.em().createQuery("select n from ProjectTreeNode n where n.treeId = :treeId and :path like concat(n.path, '%') order by length(n.path) desc")
                .setParameter("treeId", treeId)
                .setParameter("path", path).getResultList

        val pathElements: Array[String] = path.split('/').splitAt(1)._2 // chop off first part, first element is always ""
        var delta: Int = pathElements.length - 1
        var parentNode: ProjectTreeNode = null
        parents.headOption match {
          case Some(parent: ProjectTreeNode) => {
            delta = pathElements.length - parent.getLevel.asInstanceOf[Int] - 1
            parentNode = parent;
          }
          case None if pathElements.length == 1 => {
            // if we only have one element in the path then this is a root element
            delta = 1
          }
          case None if pathElements.length != 1 => {
            // create root
            val name: String = pathElements.head
            val n = populateNodeSimple(name, "/" + name, treeId, None, 0, project)

            // create root folder
            val folder: Model = createFolder(name, project, baseModelType)
            n.`type` = AbstractTree.getNodeType(folder.getClass).getName
            n.nodeId = folder.getId

            // TODO handle creation problem
            n.create()
            contextData.put(TREENODE_PREFIX + n.getPath, n)

            parentNode = n
          }
        }

        // create missing parents
        for (i <- 0 until (delta - 1)) {
          contextData.put(TREENODE_PREFIX + parentNode.getPath, parentNode)
          parentNode = createParentNode(pathElements(parentNode.getLevel.asInstanceOf[Int] + 1), parentNode, treeId, project, baseModelType)
        }

        if (parentNode != null) {
          // create the node itself
          createNode(pathElements.last, pathElements.length, parentNode, treeId, project, nodeType)
        } else {
          val n = populateNodeSimple(pathElements.head, "/" + pathElements.head, treeId, None, 0, project)
          n.`type` = nodeType
          n
        }
      }
    }

    contextData.put(TREENODE_PREFIX + node.getPath, node)
    contextData.put(PREVIOUS_TREENODE, node)

    // return nothing since this is an associated object
    null

  }

  def createNode(name: String, level: Int, parent: ProjectTreeNode, treeId: String, project: Project, nodeType: String): ProjectTreeNode = {
    val n = populateNodeSimple(name, parent.getPath + "/" + name, treeId, Some(parent.threadRoot), level, project)
    n.`type` = nodeType
    n
  }

  def createParentNode(name: String, parent: ProjectTreeNode, treeId: String, project: Project, baseModelType: Class[_ <: Model]): ProjectTreeNode = {
    val n = populateNodeSimple(name, parent.getPath + "/" + name, treeId, Some(parent.threadRoot), parent.getLevel().asInstanceOf[Int] + 1, project)
    val folder: Model = createFolder(name, project, baseModelType)

    n.`type` = AbstractTree.getNodeType(folder.getClass).getName
    n.nodeId = folder.getId

    n.create()
    n
  }

  def populateNodeSimple(name: String, path: String, treeId: String, threadRoot: Option[ProjectTreeNode], level: Int, project: Project): ProjectTreeNode = {
    val n: ProjectTreeNode = new ProjectTreeNode(project)
    n.name = name
    n.path = path
    n.treeId = treeId
    n.threadRoot = threadRoot.getOrElse(n)
    n.level = level
    n.opened = true
    n.nodeId = new java.lang.Long(-1L)
    n
  }

  def createFolder(name: String, project: Project, baseModelType: Class[_ <: Model]): Model = {
    // create the associated folder
    val clazz: Class[_ <: ProjectModel] = modelFolderMapping.get(baseModelType.getName).get
    val constructor: Constructor[_ <: ProjectModel] = clazz.getConstructor(classOf[Project])
    val folder: ProjectModel = constructor.newInstance(project)
    val setter: Method = clazz.getMethod("setName", classOf[String])
    setter.invoke(folder, name)

    // TODO handle creation problem
    folder.create()
    folder
  }


  def afterConvert(instance: Model, contextData: java.util.Map[String, AnyRef]) {
    val node: ProjectTreeNode = contextData.get(PREVIOUS_TREENODE).asInstanceOf[ProjectTreeNode]
    if (node.nodeId == -1) {
      Logger.debug("Associating node %s with tree node %s".format(instance.getId, node.getPath))
      node.nodeId = instance.getId
      // TODO deal with non-creation
      node.create()
    }
    contextData.remove(PREVIOUS_TREENODE)
    ()
  }

}