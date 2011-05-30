package importer

import scala.collection.mutable.Map
import java.lang.String
import play.db.jpa.Model

/**
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */

trait ModelConverter[T <: Model] {
  def convert(data: AnyRef, baseModelType: Class[_ <: Model], currentModelInstance:Model, contextData: Map[String, AnyRef]): Any
  def afterConvert(instance:Model, contextData:java.util.Map[String, AnyRef])
}