package datactil.beesolar

import scala.concurrent.{ ExecutionContext, Future }
import spray.routing.SimpleRoutingApp
import spray.routing.authentication._
import sprest.routing.RestRoutes
import sprest.security.{Session, User}


trait Routes extends RestRoutes { this: SimpleRoutingApp =>
  import spray.routing.Directives._
  import spray.httpx.SprayJsonSupport._
  import spray.httpx.encoding.Gzip
  import spray.json._
  import ExecutionContext.Implicits.global

  def js = pathPrefix("js" / Rest) { fileName =>
    get {
      encodeResponse(Gzip) { getFromResource(s"js/$fileName") }
    }
  }

  def css = pathPrefix("css" / Rest) { fileName =>
    get {
      encodeResponse(Gzip) { getFromResource(s"css/$fileName") }
    }
  }

  def img = pathPrefix("img" / Rest) { fileName =>
    get {
      encodeResponse(Gzip) { getFromResource(s"img/$fileName") }
    }
  }

  def index = path("") {
    get {
      //authenticate(BasicAuth("Bee Solar")) { user =>
        getFromResource("html/index.html")
      //} 
    }
  }

  def views = pathPrefix("html" / Rest) { fileName =>
    get {
      encodeResponse(Gzip) { getFromResource(s"html/$fileName") }
    }
  }

  def auth = path("auth") {
    get {
      authenticate(BasicAuth("Bee Solar")) { user =>
        complete("OK")
      } 
    }
  }

  def api = pathPrefix("api") {
    //authenticate(BasicAuth()) { user =>
      dynamic(
        restString("reminders", DB.Reminders) ~
        restString("hotspots", DB.Hotspots) ~
        restString("energys", DB.Energys)
      )
    //}

  }

  def routes = index ~ views ~ js ~ css ~ img ~ api ~ auth
}
