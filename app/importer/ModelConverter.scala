package importer

import scala.collection.mutable.Map
import models.general.{TemporalModel, CompositeModel}
import models.tm.{TMUser, Requirement}

/**
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */

trait ModelConverter[T <: TemporalModel] {

  def convert(data: AnyRef, contextData: Map[String, AnyRef]): T

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
  def convert(data: AnyRef, contextData: Map[String, AnyRef]) = null

  val afterConvert = null
}