package datactil.beesolar

import scala.concurrent.duration._
import akka.pattern.ask
import akka.util.Timeout
import spray.routing.HttpServiceActor
import spray.can.Http
import spray.http._
import HttpMethods._
import spray.can.server.Stats
import spray.http.{HttpRequest, Uri}

class Mercury extends HttpServiceActor {
  implicit val timeout: Timeout = 13.second
  import context.dispatcher
  import MediaTypes._

  case class EnergyStats(connections:Int, meanTime:Int)

  def stats(s: Stats) = HttpResponse(
    entity = HttpEntity(`text/html`,
      <html>
        <body>
          <h1>HttpServer Stats</h1>
          <table>
            <tr><td>uptime:</td><td>{s.uptime}</td></tr>
            <tr><td>totalRequests:</td><td>{s.totalRequests}</td></tr>
            <tr><td>openRequests:</td><td>{s.openRequests}</td></tr>
            <tr><td>maxOpenRequests:</td><td>{s.maxOpenRequests}</td></tr>
            <tr><td>totalConnections:</td><td>{s.totalConnections}</td></tr>
            <tr><td>openConnections:</td><td>{s.openConnections}</td></tr>
            <tr><td>maxOpenConnections:</td><td>{s.maxOpenConnections}</td></tr>
            <tr><td>requestTimeouts:</td><td>{s.requestTimeouts}</td></tr>
          </table>
        </body>
      </html>.toString()
    )
  )

  def receive = {
    case HttpRequest(GET, Uri.Path("/stats"), _, _, _) =>
      val client = sender
      context.actorFor("/user/IO-HTTP/listener-0") ? Http.GetStats onSuccess {
        case x: Stats => client ! stats(x)
      }
    case EnergyStats(connections, meanTime) => complete("medan " + meanTime)

  }

}