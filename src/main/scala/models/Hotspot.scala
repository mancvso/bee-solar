package datactil.beesolar.models

import sprest.models._
import org.joda.time.DateTime

case class Hotspot(device:Devices.Device, user:String, start:DateTime, var notes:Option[String] = None,
    var end:Option[DateTime] = None, var mac: Option[String] = None, var id: Option[String] = None
    ) extends Model[String]

import spray.json._
object Hotspot extends ModelCompanion[Hotspot, String] {
  import sprest.Formats._

  implicit val jsonHotspotFormat = jsonFormat7(Hotspot.apply _)
}
