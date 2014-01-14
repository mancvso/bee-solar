package com.datactil.actors

import akka.actor.Actor
import spray.routing._
import spray.httpx.encoding.Gzip

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class EarthActor extends Actor with Earth {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(routes)
}


// this trait defines our service behavior independently from the service actor
trait Earth extends HttpService {

  def js = pathPrefix("js" / Rest) { fileName:String =>
    get {
      encodeResponse(Gzip) { getFromResource(s"js/$fileName") }
    }
  }

  def css = pathPrefix("css" / Rest) { fileName =>
    get {
      encodeResponse(Gzip) { getFromResource(s"css/$fileName") }
    }
  }

  def fonts = pathPrefix("fonts" / Rest) { fileName =>
    get {
      encodeResponse(Gzip) { getFromResource(s"fonts/$fileName") }
    }
  }

  def img = pathPrefix("img" / Rest) { fileName =>
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


  def views = pathPrefix("html" / Rest) { fileName =>
    get {
      encodeResponse(Gzip) { getFromResource(s"html/$fileName") }
    }
  }

  val routes = index ~ admin ~ views ~ js ~ css ~ img ~ fonts
}