package models

import org.joda.time.{Period, DateTime}

/**
 * Created by brianbvidal on 07-01-14.
 */
trait Client {
  val start:DateTime
  var end:Option[DateTime]

  def leaseTime:Option[Period] = {
    end.map { e => new Period(start, e) }
  }
}
