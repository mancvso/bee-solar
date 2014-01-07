package datactil.beesolar.models

import sprest.models._
import org.joda.time.DateTime
import models.Client

case class HotspotClient (

  device:Devices.Device,
  user:String,
  start:DateTime = DateTime.now(),

  var notes:Option[String] = None,
  var end:Option[DateTime] = None,
  var mac: Option[String] = None,
  var ip: Option[String] = None,
  var id: Option[String] = None

) extends Client with Model[String]

import spray.json._
object HotspotClient extends ModelCompanion[HotspotClient, String] {
  import sprest.Formats._

  implicit val jsonHotspotFormat = jsonFormat8(HotspotClient.apply _)
}
