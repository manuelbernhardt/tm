package importer

import scala.collection.mutable.Map
import models.general.{TemporalModel, CompositeModel}
import models.tm.{TMUser, Requirement}
import play.db.jpa.JPA
import java.lang.String
import collection.JavaConversions._
import java.util.List
import javax.persistence.TypedQuery

/**
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */

trait ModelConverter[T <: TemporalModel] {

  def convert(data: AnyRef, contextData: Map[String, AnyRef]): Option[T]

  val afterConvert: Map[String, AnyRef] => Unit
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
    val query:TypedQuery[TMUser] = JPA.em().createQuery("from TMUser u where concat(concat(u.user.name, ' '), u.user.surname) = :fullName and u.account.id = :accountId", classOf[TMUser])
    query.setParameter("fullName", data.asInstanceOf[String])
    query.setParameter("accountId", contextData.get("accountId"))
    val user: List[_] = query.getResultList
    user.length match {
      case 0 => null // no user found, we could create one...
      case 1 => Option(user.get(0).asInstanceOf[TMUser])
      case _ => null // TODO add import error
    }
  }

  val afterConvert = null
}