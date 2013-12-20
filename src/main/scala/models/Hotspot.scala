package datactil.beesolar.models

import sprest.models._
import org.joda.time.DateTime

case class Hotspot(device:String, user:String, start:DateTime, var notes:Option[String] = None,
    var end:Option[DateTime] = None, var mac: Option[String] = None, var id: Option[String] = None
    ) extends Model[String]

object Hotspot extends ModelCompanion[Hotspot, String] {
  import sprest.Formats._
  implicit val jsonFormat = jsonFormat7(Hotspot.apply _)
}