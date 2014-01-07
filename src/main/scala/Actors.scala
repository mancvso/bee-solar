package datactil.beesolar

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorRef
import akka.actor.Terminated
import akka.actor.Props
import akka.util.{ByteString, ByteStringBuilder}
import akka.io.IO
import com.github.jodersky.flow.Serial._
import com.github.jodersky.flow.Serial
import com.github.jodersky.flow.SerialSettings
import datactil.beesolar.DB.{AlertDAO, EnergyDAO}
import datactil.beesolar.models.Alert
import sprest.util.enum._

sealed abstract class SerialMessageKind(name: String) extends Enum[SerialMessageKind](name)
object SerialMessageKind extends EnumCompanion[SerialMessageKind] {
  case object EnergyClientConnected extends SerialMessageKind("E")
  case object InternalAlert extends SerialMessageKind("A")
  case object EnergyClientDisconected extends SerialMessageKind("X")

  register(
    EnergyClientConnected,
    InternalAlert,
    EnergyClientDisconected)
}



case class SerialMessage(messageKind:SerialMessageKind, code:Option[Int] = None)

class Mercury(settings: SerialSettings) extends Actor with ActorLogging {
  import Mercury._
  import context._

  private val buffer = new ByteStringBuilder
  //val reader = actorOf(Props[ConsoleReader])

  val venus = system.actorOf(Props[Venus])

  override def preStart() = {
    log.info(s"Requesting manager to open port: ${settings.port}, baud: ${settings.baud}")
    IO(Serial) ! Serial.Open(settings)
  }

  override def postStop() = {
    system.shutdown()
  }

  def receive = {
    case CommandFailed(cmd, reason) => {
      log.error(s"Connection failed, stopping terminal. Reason: ${reason}")
      context stop self
    }
    case Opened(s, _) => {
      log.info(s"Port ${s.port} is now open.")
      val operator = sender
      context become opened(operator)
      context watch operator
      operator ! Register(self)
      //reader ! ConsoleReader.Read
    }
  }

  def opened(operator: ActorRef): Receive = {

    case Received(data) => {
      // Detectar el fin de un mensaje
      data.map { b =>
        buffer += b
        // buffer data until newline+carriage return is detected
        if(b == 10){
          venus ! parseMessage(buffer.result.decodeString("UTF-8").stripLineEnd) // String->SerialMessage
          buffer.clear()
        }

      }
      log.info(s"Received data: ${formatData(data)}")
    }

    case Wrote(data) => log.info(s"Wrote data: ${formatData(data)}")

    case Closed => {
      log.info("Operator closed normally, exiting terminal.")
      context unwatch operator
      context stop self
    }

    case Terminated(`operator`) => {
      // TODO: Handle termination
      log.error("Operator crashed, exiting terminal.")
      context stop self
    }

    /*case ConsoleReader.EOT => {
      log.info("Initiating close.")
      operator ! Close
    }

    case ConsoleReader.ConsoleInput(input) => {
      val data = ByteString(input.getBytes)
      operator ! Write(data, Wrote(data))
      reader ! ConsoleReader.Read
    }*/
  }
}

object Mercury {

  case class Wrote(data: ByteString) extends Event

  def apply(settings: SerialSettings) = Props(classOf[Mercury], settings)

  private def formatData(data: ByteString):String = {
    data.mkString("[", ",", "]") + " " + (new String(data.toArray, "UTF-8"))
  }

  private def parseMessage(msg: String):SerialMessage = {
    val preCode = msg.tail
    var code:Option[Int] = None

    if(!preCode.isEmpty){
      code = Option(preCode.toInt)
    }

    SerialMessage( SerialMessageKind.withName(msg.head.toString), code)
  }

}

/**
 * Take Serial messages and comunicate with the DB
 */
import scala.concurrent.ExecutionContext.Implicits.global
class Venus extends Actor with ActorLogging {
  def receive: Actor.Receive = {
    case SerialMessage(SerialMessageKind.EnergyClientConnected, _) => EnergyDAO.add( models.EnergyClient() )
    case SerialMessage(SerialMessageKind.InternalAlert, code) => {
      AlertDAO.add( Alert("some-message", code.getOrElse(0), models.Severities.Medium) )
    }
  }
}
