package com.example

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http

// Akka base setup
object WebApp extends App {

  // create and start our service actor
  implicit val system = ActorSystem("on-spray-can")

  val service = system.actorOf(Props[MyServiceActor], "my-service")

  // create a new HttpServer using our handler tell it where to bind to
  IO(Http) ! Http.Bind(service, interface = "localhost", port = 8080)
}
