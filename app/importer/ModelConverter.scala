package importer

import scala.collection.mutable.Map
import java.lang.String
import play.db.jpa.GenericModel

/**
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */

trait ModelConverter[T <: GenericModel] {
  def convert(data: AnyRef, baseModelType: Class[_ <: GenericModel], currentModelInstance:GenericModel, contextData: Map[String, AnyRef]): Any
  val afterConvert: Map[String, AnyRef] => Unit
}