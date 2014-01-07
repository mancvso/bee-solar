package datactil.beesolar.models

import sprest.models._
import org.joda.time.{DateTime, Period}
import models.Client

// TODO: Add time field transient+dinamic
case class EnergyClient (

  start:DateTime = DateTime.now,

  var end:Option[DateTime] = None,
  var id: Option[String] = None

) extends Client with Model[String]

object EnergyClient extends ModelCompanion[EnergyClient, String] {
  import sprest.Formats._
  implicit val jsonFormat = jsonFormat3(EnergyClient.apply _)
}