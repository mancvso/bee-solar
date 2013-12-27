package datactil.beesolar

import akka.actor.Actor
import org.joda.time.DateTime
import spray.http.StatusCodes
import spray.routing.{RequestContext}

case class Ping(id:String, date:DateTime, ctx:RequestContext)
case class Broadcast(data:String)
case class Message(data:String)

/*class Comet extends Actor {

  var requests: Map[String, RequestContext] = Map.empty

  def receive = {
    case Ping(id, date, ctx) => requests += (id -> ctx)
    case Broadcast(data) =>requests.map { e => e._2.complete(StatusCodes.OK) }
  }

}*/
