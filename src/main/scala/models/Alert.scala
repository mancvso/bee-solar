package datactil.beesolar.models

import spray.json._
import sprest.models._
import org.joda.time.DateTime
import sprest.reactivemongo.typemappers._

case class Alert(message:String, code:Int, severity: Severities.Severity, date:DateTime = DateTime.now(),
    var id: Option[String] = None ) extends Model[String]

object Alert extends ModelCompanion[Alert, String] {
  import sprest.Formats._

  implicit val jsonFormat = jsonFormat5(Alert.apply _)
}
