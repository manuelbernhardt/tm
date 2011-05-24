package importer

import scala.collection.mutable.Map
import java.lang.String
import models.general.{CompositeModel, TemporalModel}

/**
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */

trait ModelConverter[T <: TemporalModel] {
  def convert(data: AnyRef, baseModelType: Class[_ <: CompositeModel], contextData: Map[String, AnyRef]): Any
  val afterConvert: Map[String, AnyRef] => Unit
}