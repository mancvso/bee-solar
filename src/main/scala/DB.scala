package datactil.beesolar

import sprest.reactivemongo.ReactiveMongoPersistence
import sprest.reactivemongo.typemappers._
import spray.json.RootJsonFormat
import com.typesafe.config._

object DB extends ReactiveMongoPersistence {

  import reactivemongo.api._
  import sprest.models.UUIDStringId
  import sprest.models.UniqueSelector
  import models._
  import scala.concurrent.ExecutionContext

  val conf = ConfigFactory.load()
  val driver = new MongoDriver
  val connection = driver.connection(List(conf.getString("mongo.server")))
  lazy val db = connection(conf.getString("app.name"))(Main.system.dispatcher)

  // Json mapping to / from BSON - in this case we want "_id" from BSON to be 
  // mapped to "id" in JSON in all cases
  implicit object JsonTypeMapper extends SprayJsonTypeMapper with NormalizedIdTransformer

  abstract class UnsecuredDAO[M <: sprest.models.Model[String]](collName: String)(implicit jsformat: RootJsonFormat[M]) extends CollectionDAO[M, String](db(collName)) {

    case class Selector(id: String) extends UniqueSelector[M, String]

    override def generateSelector(id: String) = Selector(id)
    override protected def addImpl(m: M)(implicit ec: ExecutionContext) = doAdd(m)
    override protected def updateImpl(m: M)(implicit ec: ExecutionContext) = doUpdate(m)
    override def remove(selector: Selector)(implicit ec: ExecutionContext) = uncheckedRemoveById(selector.id)
  }

  // MongoDB collections:
  object EnergyDAO extends UnsecuredDAO[EnergyClient]("energys") with UUIDStringId
  object HotspotDAO extends UnsecuredDAO[HotspotClient]("hotspots") with UUIDStringId
  object AlertDAO extends UnsecuredDAO[Alert]("alerts") with UUIDStringId

}

