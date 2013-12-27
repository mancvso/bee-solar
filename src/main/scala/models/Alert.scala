package datactil.beesolar.models

import spray.json._
import sprest.models._
import org.joda.time.DateTime
import sprest.reactivemongo.typemappers._

case class Alert(message:String, date:DateTime, severity: Severities.Severity,
    var id: Option[String] = None ) extends Model[String]

object Alert extends ModelCompanion[Alert, String] {
  import sprest.Formats._

  implicit val jsonFormat = jsonFormat4(Alert.apply _)
}
