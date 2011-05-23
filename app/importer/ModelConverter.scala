package importer

import scala.collection.mutable.Map
import models.general.TemporalModel
import java.lang.String

/**
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */

trait ModelConverter[T <: TemporalModel] {
  def convert(data: AnyRef, contextData: Map[String, AnyRef]): Any
  val afterConvert: Map[String, AnyRef] => Unit
}