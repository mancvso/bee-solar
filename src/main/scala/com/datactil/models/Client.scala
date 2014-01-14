package com.datactil.models

import org.joda.time.{Period, DateTime}

trait Client {
  val start:DateTime
  var end:Option[DateTime]

  def leaseTime:Option[Period] = {
    end.map { e => new Period(start, e) }
  }
}