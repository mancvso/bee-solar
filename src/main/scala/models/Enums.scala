package datactil.beesolar.models

import sprest.models._
import spray.json._

object Devices extends Enumeration {
  type Device = Value
  
  val Laptop  = Value("LAPTOP")
  val Mobile  = Value("MOBILE")
  val Tablet  = Value("TABLET")
  val Console = Value("CONSOLE")
  val Other   = Value("OTHER")

  implicit object DeviceJsonFormat extends JsonFormat[Devices.Device] {
    def write(obj: Devices.Device): JsValue = JsString(obj.toString)

    def read(json: JsValue): Devices.Device = json match {
      case JsString(str) => Devices.withName(str.toUpperCase)
      case _ => throw new DeserializationException("Enum string expected")
    }
  }
  
}

// object Device {
//   sealed trait Value { def name:String }

//   case object Laptop extends Device.Value { val name = "LAPTOP" }
//   case object Mobile extends Device.Value { val name = "MOBILE" }
//   case object Tablet extends Device.Value { val name = "TABLET" }
//   case object Console extends Device.Value { val name = "CONSOLE" }
//   case object Other extends Device.Value { val name = "OTHER" }
  
//   val values = Seq(Laptop, Mobile, Tablet, Console, Other)

//   implicit object DeviceJsonFormat extends JsonFormat[Device.Value] {
//     def write(obj: Device.Value): JsValue = JsString(obj.name)

//     def read(json: JsValue): Device.Value = json match {
//       case JsString(str) => {
//         Device.values find (_.name == str) getOrElse { throw new DeserializationException("No such value") }
//       }
//       case _ => throw new DeserializationException("Enum string expected")
//     }
//   }

// }

object Severities extends Enumeration {
  type Severity = Value
  
  val Low    = Value("LOW")
  val Medium = Value("MEDIUM")
  val High   = Value("HIGH")

  implicit object SeverityJsonFormat extends JsonFormat[Severities.Severity] {
    def write(obj: Severities.Severity): JsValue = JsString(obj.toString)

    def read(json: JsValue): Severities.Severity = json match {
      case JsString(str) => Severities.withName(str.toUpperCase)
      case _ => throw new DeserializationException("Enum string expected")
    }
  }

}