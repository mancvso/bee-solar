package com.datactil.models

/**
 * Created by brianbvidal on 14-01-14.
 */

import sprest.models._
import org.joda.time.DateTime

case class HotspotClient (
  device:Device,
  user:String,
  start:DateTime = DateTime.now(),

  var notes:Option[String] = None,
  var end:Option[DateTime] = None,
  var mac: Option[String] = None,
  var ip: Option[String] = None,
  var id: Option[String] = None

) extends Client with Model[String]

object HotspotClient extends ModelCompanion[HotspotClient, String] {
  import sprest.Formats._

  implicit val jsonHotspotFormat = jsonFormat8(HotspotClient.apply _)
}