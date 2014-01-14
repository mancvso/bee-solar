package com.datactil

import actors._
import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import com.github.jodersky.flow.SerialSettings
import com.typesafe.config.ConfigFactory

object Boot extends App {

  val conf = ConfigFactory.load()

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("on-spray-can")

  // create and start our service actor
  val httpService = system.actorOf(Props[EarthActor], "http-service")

  val settings = SerialSettings(conf.getString("serial.port"), conf.getInt("serial.baud"))
  val serialService = system.actorOf(MercuryActor(settings), name = "serial-service")

  implicit val timeout = Timeout(5.seconds)
  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ? Http.Bind(httpService, interface = "localhost", port = 8080)
}
