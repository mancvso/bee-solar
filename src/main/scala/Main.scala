package datactil.beesolar

/**
 * == Convention ==
 * FooBar        : Class, Reference
 * t             : Tuple
 * a             : Array, Single-value Secuence, ie, List, Vector
 * m             : Multi-array, Multiple-value Secuence, ie, Map or Dictionary
 * s             : String
 * i/d/l/n/x/X   : Number (Int, Double, Long, x:lower non-native numeric type, X:greater..., ie, BigInt, BigLong)
 * is/has        : Boolean
 * do/get/set/on : Action (Method or Function, Getter, Setter, Callback)
 * _             : Private.
 **/

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

