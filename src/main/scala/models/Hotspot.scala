package datactil.beesolar.models

import spray.json._
import sprest.models._
import org.joda.time.DateTime
import sprest.reactivemongo.typemappers._

case class Hotspot(device:String, user:String, start:DateTime, var end:Option[DateTime] = None,
    var mac: Option[String] = None, var id: Option[String] = None ) extends Model[String]

object Hotspot extends ModelCompanion[Hotspot, String] {
  import sprest.Formats._
  implicit val jsonFormat = jsonFormat6(Hotspot.apply _)
}