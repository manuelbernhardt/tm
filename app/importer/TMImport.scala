package importer

import collection.JavaConversions._
import models.tm.{TMUser, Requirement}
import play.db.jpa.JPA
import javax.persistence.TypedQuery
import collection.mutable.Map
import java.lang.String
import models.tm.test.Tag

/**
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */

object TMImport {
  val ACCOUNT_ID: String = "accountId"
}

object RequirementConverter extends ModelConverter[Requirement] {

  def convert(data: AnyRef, contextData: Map[String, AnyRef]) = {
    println("CONVERTING TO REQUIREMENT")
    null;
  }

  val afterConvert = null
}

object TMUserConverter extends ModelConverter[TMUser] {
  def convert(data: AnyRef, contextData: Map[String, AnyRef]) = {

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
  def convert(data: AnyRef, contextData: Map[String, AnyRef]) = null

  val afterConvert = null
}