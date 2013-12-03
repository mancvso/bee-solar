package datactil.beesolar.models

import spray.json._
import sprest.models._
import org.joda.time.DateTime
import sprest.reactivemongo.typemappers._

case class Energy(device:String, start:DateTime, end:Option[DateTime],
    var id: Option[String] = None ) extends Model[String]

object Energy extends ModelCompanion[Energy, String] {
  import sprest.Formats._
  implicit val jsonFormat = jsonFormat4(Energy.apply _)
}