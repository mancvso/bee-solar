package datactil.beesolar.models

import spray.json._
import sprest.models._
import org.joda.time.DateTime
import sprest.reactivemongo.typemappers._

case class Hotspot(device:String, user:String, start:DateTime, end:Option[DateTime],
    var id: Option[String] = None ) extends Model[String]

object Hotspot extends ModelCompanion[Hotspot, String] {
  import sprest.Formats._
  implicit val jsonFormat = jsonFormat5(Hotspot.apply _)
}