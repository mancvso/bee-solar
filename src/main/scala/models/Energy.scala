package datactil.beesolar.models

import spray.json._
import sprest.models._
import org.joda.time.{DateTime, Period}
import sprest.reactivemongo.typemappers._

// TODO: Add time field transient+dinamic
case class Energy(device:Devices.Device, start:DateTime = DateTime.now,
  var end:Option[DateTime] = None, var id: Option[String] = None ) extends Model[String]

object Energy extends ModelCompanion[Energy, String] {
  import sprest.Formats._
  implicit val jsonFormat = jsonFormat4(Energy.apply _)
  
}