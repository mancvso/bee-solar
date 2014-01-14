package com.datactil.security

import sprest.security.{ Session => SprestSession, User }

case class Session() extends SprestSession {
  type ID = String
  override val sessionId = "singletonsession"
  override val user = new User {
    type ID = String
    override val userId = "singletonuser"
  }

}

import spray.json._
import sprest.models._
import org.joda.time.DateTime
import sprest.reactivemongo.typemappers._

case class AuthIntent(token:String, username:String, saltedPass:String,
    var id: Option[String] = None ) extends Model[String]

object AuthIntent extends ModelCompanion[AuthIntent, String] {
  import sprest.Formats._
  implicit val jsonFormat = jsonFormat4(AuthIntent.apply _)
}