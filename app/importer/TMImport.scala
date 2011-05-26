package importer

import collection.JavaConversions._
import javax.persistence.TypedQuery
import collection.mutable.Map
import java.lang.String
import models.tm.test.Tag
import models.tm.test.Tag.TagType
import java.lang.reflect.Method
import tree.persistent.AbstractTree
import controllers.RequirementTree
import models.tm._
import play.db.jpa.{GenericModel, JPA}

/**
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */

object TMImport {
  val ACCOUNT_ID: String = "accountId"
  val PROJECT: String = "project"

  val modelConverters = Map[String, ModelConverter[_]](
    classOf[TMUser].getName -> TMUserConverter,
    classOf[Tag].getName -> TagsConverter
  )

  val cellConverters = Map[String, List[ColumnImportRule]](
    classOf[Requirement].getName -> List(
      StringColumnImportRule(0, "name"),
      StringColumnImportRule(1, "description"),
      UserColumnImportRule(2, "createdBy"),
      TagsColumnImportRule(3, "tags")
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

  def create( baseModelType: Class[_ <: GenericModel], instance: AnyRef):Boolean = {
    // investigate this...
    val create: Method = baseModelType.getMethod("create")
    create.invoke(instance).asInstanceOf[Boolean]
  }
}

object TMUserConverter extends ModelConverter[TMUser] {
  def convert(data: AnyRef, entity: Class[_ <: GenericModel], currentModelInstance:GenericModel, contextData: Map[String, AnyRef]) = {

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

  val afterConvert = null
}

object TagsConverter extends ModelConverter[Tag] with TMConverter {
  def convert(data: AnyRef, baseModelType: Class[_ <: GenericModel], currentModelInstance:GenericModel, contextData: Map[String, AnyRef]) = {

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
          create(baseModelType, tag)

          tag
        }
        case 1 => tags.get(0)
        case _ => throw new ImportError("Too many tags with name %s and type %s".format(data, tagType.name)) // TODO add import error instead of throwing it here
      }
      tagInstance
    }

    asJavaList(result.toList)
  }

  val afterConvert = null
}

/** this converter is special in that it does not return anything but persists new nodes **/
object TreePathConverter extends ModelConverter[ProjectTreeNode] with TMConverter {

  val TREENODE_PREFIX = "treeNode_"

  val modelTreeMapping:Map[String, Class[_ <: AbstractTree]] = Map(classOf[Requirement].getName -> classOf[RequirementTree])

  def getTreeId(c:Class[_ <: GenericModel]) = {
    val name: String = modelTreeMapping.get(c.getName).get.getSimpleName
    name.substring(0, 1).toLowerCase() + name.substring(1)
  }
  def convert(data: AnyRef, baseModelType: Class[_ <: GenericModel], currentModelInstance:GenericModel, contextData: Map[String, AnyRef]) = {

    val path: String = data.asInstanceOf[String]

    // first, check if we already created or retrieved this path
    contextData.get(TREENODE_PREFIX + path) match {
      case Some(parent:ProjectTreeNode) => {
        val n:ProjectTreeNode = new ProjectTreeNode(getProject(contextData))

        // TODO see if we can do this in a better way...
        var name:String = null;
        try {
          val nameGetter = baseModelType.getMethod("getName")
          name = nameGetter.invoke(currentModelInstance).asInstanceOf[String]
        } catch {
          case _ => throw new ImportError("Cannot figure out name of %s".format(baseModelType.getSimpleName))// TODO add import error instead of throwing it here
        }

        n.name = name
        n.path = parent.getPath + "/" + name
        n.treeId = parent.treeId
        n.threadRoot = parent.threadRoot
        n.treeId = getTreeId(baseModelType)
        n.level = path.split("/").length
        n.opened = true

        // TODO deal with non-creation
        create(baseModelType, n)

        contextData.put(TREENODE_PREFIX + path, n)

      }
      case None => {
        // look if there is already a node with this path
      }
    }

  }

  val afterConvert = null


}