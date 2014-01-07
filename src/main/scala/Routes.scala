package datactil.beesolar

import scala.concurrent.{ ExecutionContext}
import spray.routing.{RequestContext, SimpleRoutingApp, HttpService}
import spray.routing.authentication._
import sprest.routing.RestRoutes

import spray.http.StatusCodes

trait Routes extends HttpService with RestRoutes { this: SimpleRoutingApp =>
  import spray.httpx.SprayJsonSupport._
  import spray.httpx.encoding.Gzip
  import ExecutionContext.Implicits.global
  import com.typesafe.config._
  import datactil.beesolar.security.AuthIntent
  
  val confx = ConfigFactory.load()
  //val cometActor = actorRefFactory.actorOf(Props[Comet])

  def js = pathPrefix("js" / Rest) { fileName:String =>
    get {
      encodeResponse(Gzip) { getFromResource(s"js/$fileName") }
    }
  }

  def css = pathPrefix("css" / Rest) { fileName:String =>
    get {
      encodeResponse(Gzip) { getFromResource(s"css/$fileName") }
    }
  }

  def fonts = pathPrefix("fonts" / Rest) { fileName:String =>
    get {
      encodeResponse(Gzip) { getFromResource(s"fonts/$fileName") }
    }
  }

  def img = pathPrefix("img" / Rest) { fileName:String =>
    get {
      encodeResponse(Gzip) { getFromResource(s"img/$fileName") }
    }
  }

  def admin = path("admin") {
    get {
      getFromResource("html/admin.html")
    }
  }

  def index = path("") {
    get {
      getFromResource("html/index.html")
    }
  }

  def comet = path("comet"){
    get { ctx:RequestContext =>
      //cometActor ! Ping("some", DateTime.now(), ctx)
      complete(StatusCodes.OK)
    }
  }


  def views = pathPrefix("html" / Rest) { fileName:String =>
    get {
      encodeResponse(Gzip) { getFromResource(s"html/$fileName") }
    }
  }

  def auth = path("auth") {
    post {
       entity(as[AuthIntent]) { authIntent =>
        val password = confx.getString("spray.routing.users.password")
        val username = confx.getString("spray.routing.users.username")
        // XXX: SHA-1
        val checkSalted = security.Base64.encode( password + ":" + authIntent.token)
        val hasMatch:Boolean = checkSalted.diff(authIntent.saltedPass).trim.length == 0

        if(authIntent.username == username && hasMatch){
          complete("OK")
        } else {
          complete("UNAUTHORIZED")
        }
      }
    }
  }

  def api = pathPrefix("api") {
    // Las credenciales vienen dadas por spray.routing.users en application.conf
    authenticate(BasicAuth()) { user =>
      dynamic (
        restString("hotspots", DB.HotspotDAO) ~ restString("alerts", DB.AlertDAO) ~ restString("energys", DB.EnergyDAO)
      )
    }
  }

  def routes = index ~ admin ~ views ~ js ~ css ~ img ~ fonts ~ api ~ auth ~ comet
}
