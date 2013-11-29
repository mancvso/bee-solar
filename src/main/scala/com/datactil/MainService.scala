package com.example

import akka.actor.Actor
import spray.routing._
import directives.LogEntry
import spray.http._
import MediaTypes._
import akka.event.Logging
import java.io.File
import shapeless._
import spray.routing.authentication.{BasicAuth, UserPass}
import scala.concurrent.{Future, ExecutionContext}
import ExecutionContext.Implicits.global


// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class MainServiceActor extends Actor with MainService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}

// this trait defines our service behavior independently from the service actor
trait MainService extends HttpService with StaticResources with TwirlPages with SecurePages {

  def showPath(req: HttpRequest) = LogEntry("Method = %s, Path = %s" format(req.method, req.uri), Logging.InfoLevel)

  val myRoute =
    logRequest(showPath _) {
      staticResources ~ securePages ~ twirlPages
    }
}

// Trait for serving static resources
// Sends 404 for 'favicon.icon' requests and serves static resources in 'bootstrap' folder.
trait StaticResources extends HttpService {

  val staticResources =
    get {
      path("favicon.ico") {
        complete(StatusCodes.NotFound)
      } ~
      path(Rest) { path =>
        getFromResource("static/%s" format path)
      } ~
      path("file") {
        getFromResource("application.conf")
      }
    }
}

// Trait for serving some dynamic Twirl pages
trait TwirlPages extends HttpService {

  def twirl(pathStr:String)(template: => Any) = {
    path(pathStr) {
      respondWithMediaType(`text/html`) {
        complete(template.toString)
      }
    }
  }

  val twirlPages = get {
      twirl(""){
        html.index()
      } ~
      twirl("login"){
        html.login()
      }
    }
}

trait SecurePages extends HttpService {

  def userAuth(userPass: Option[UserPass]): Future[Option[String]] = Future {
    if (userPass.exists(up => up.user == "datactil" && up.pass == "datactil")) Some("datactil")
    else None
  }

  val securePages = //sealRoute {
    path("dashboard") {
      authenticate(BasicAuth(userAuth _, realm = "datactil")) { userName =>
        complete(s"The user is '$userName'")
      }
    }
  //}


}