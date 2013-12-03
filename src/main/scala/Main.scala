package datactil.beesolar

import akka.actor.ActorSystem
import spray.routing.SimpleRoutingApp
import spray.httpx.TwirlSupport
import spray.httpx.encoding.Gzip

import com.typesafe.config._

object Main extends App with SimpleRoutingApp with spray.httpx.SprayJsonSupport with Routes {

  val conf = ConfigFactory.load()
  override implicit val system = ActorSystem(conf.getString("app.name"))

  startServer(interface = conf.getString("app.host"), port = conf.getInt("app.port")) {
    routes
  }
}

