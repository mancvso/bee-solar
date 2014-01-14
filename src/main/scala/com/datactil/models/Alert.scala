package com.datactil.models

/**
 * Created by brianbvidal on 14-01-14.
 */
import sprest.models._
import org.joda.time.DateTime

case class Alert(message:String, code:Int, severity: Severity, date:DateTime = DateTime.now(),
                 var id: Option[String] = None ) extends Model[String]

object Alert extends ModelCompanion[Alert, String] {
  import sprest.Formats._

  implicit val jsonFormat = jsonFormat5(Alert.apply _)
}
