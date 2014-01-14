package com.datactil.actors

import akka.io.IO
import akka.actor.{Terminated, ActorRef, ActorLogging, Actor, Props}
import akka.util.{ByteString, ByteStringBuilder}
import com.github.jodersky.flow.Serial
import com.github.jodersky.flow.Serial._
import com.github.jodersky.flow.Serial.Received
import com.github.jodersky.flow.Serial.Register
import com.github.jodersky.flow.SerialSettings
import com.github.jodersky.flow.Serial.CommandFailed
import com.github.jodersky.flow.Serial.Opened

/**
 * Created by brianbvidal on 14-01-14.
 */
class MercuryActor(settings: SerialSettings) extends Actor with ActorLogging {
  import MercuryActor._
  import context._

  private val buffer = new ByteStringBuilder

  override def preStart() = {
    log.info(s"Requesting manager to open port: ${settings.port}, baud: ${settings.baud}")
    IO(Serial) ! Serial.Open(settings)
  }

  override def postStop() = {
    //system.shutdown()
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
    }
  }

  def opened(operator: ActorRef): Receive = {

    case Received(data) => {
      // Detectar el fin de un mensaje
      data.map { b =>
        buffer += b
        // buffer data until newline+carriage return is detected
        if(b == 10){
          val msg = buffer.result.decodeString("UTF-8").stripLineEnd
          //venus ! parseMessage(msg) // String->SerialMessage
          log.info( msg )
          buffer.clear()
        }

      }
      //log.info(s"Received data: ${formatData(data)}")
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

  }
}

object MercuryActor {

  case class Wrote(data: ByteString) extends Event

  def apply(settings: SerialSettings) = Props(classOf[MercuryActor], settings)

  private def formatData(data: ByteString):String = {
    data.mkString("[", ",", "]") + " " + (new String(data.toArray, "UTF-8"))
  }

  /*private def parseMessage(msg: String):SerialMessage = {
    val preCode = msg.tail
    var code:Option[Int] = None

    if(!preCode.isEmpty){
      code = Option(preCode.toInt)
    }

    SerialMessage( SerialMessageKind.withName(msg.head.toString), code)
  }*/

}