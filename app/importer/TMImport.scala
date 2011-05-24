package importer

import collection.JavaConversions._
import play.db.jpa.JPA
import javax.persistence.TypedQuery
import collection.mutable.Map
import java.lang.String
import models.tm.test.Tag
import models.general.CompositeModel
import models.tm.test.Tag.TagType
import java.lang.reflect.Method
import models.tm.{Project, TMUser, Requirement}

/**
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */

object TMImport {
  val ACCOUNT_ID: String = "accountId"
  val PROJECT: String = "project"
}

object RequirementConverter extends ModelConverter[Requirement] {

  def convert(data: AnyRef, baseModelType: Class[_ <: CompositeModel], contextData: Map[String, AnyRef]) = {
    println("CONVERTING TO REQUIREMENT")
    null;
  }

  val afterConvert = null
}

object TMUserConverter extends ModelConverter[TMUser] {
  def convert(data: AnyRef, baseModelType: Class[_ <: CompositeModel], contextData: Map[String, AnyRef]) = {

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

object TagsConverter extends ModelConverter[Tag] {
  def convert(data: AnyRef, baseModelType: Class[_ <: CompositeModel], contextData: Map[String, AnyRef]) = {

    val tagType: TagType = TagType.getFromClass(baseModelType)

    val result: Array[Tag] = for (t <- data.asInstanceOf[String].split(',')) yield {
      val q: TypedQuery[Tag] = JPA.em().createQuery("from Tag t where t.name = :tagName and t.type = :tagType", classOf[Tag])
      q.setParameter("tagName", t.trim)
      q.setParameter("tagType", tagType)
      val tags: java.util.List[Tag] = q.getResultList
      val tagInstance: Tag = tags.length match {
        case 0 => {
          val tag: Tag = new Tag(contextData.get(TMImport.PROJECT).get.asInstanceOf[Project]) // TODO getOrElse programmer error
          tag.name = t.trim
          tag.`type` = tagType
          // investigate this...
          val create: Method = baseModelType.getMethod("create")
          val created: Boolean = create.invoke(tag).asInstanceOf[Boolean]
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