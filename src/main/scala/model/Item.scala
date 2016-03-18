package model

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

case class Item(
  id: Long,
  itemName: Option[String] = None,
  unitPrice: Option[BigDecimal] = None
)

object Item extends SkinnyCRUDMapper[Item] {
  override lazy val tableName = "item"
  override lazy val defaultAlias = createAlias("i")

  /*
   * If you're familiar with ScalikeJDBC/Skinny ORM, using #autoConstruct makes your mapper simpler.
   * (e.g.)
   * override def extract(rs: WrappedResultSet, rn: ResultName[Item]) = autoConstruct(rs, rn)
   *
   * Be aware of excluding associations like this:
   * (e.g.)
   * case class Member(id: Long, companyId: Long, company: Option[Company] = None)
   * object Member extends SkinnyCRUDMapper[Member] {
   *   override def extract(rs: WrappedResultSet, rn: ResultName[Member]) =
   *     autoConstruct(rs, rn, "company") // "company" will be skipped
   * }
   */
  override def extract(rs: WrappedResultSet, rn: ResultName[Item]): Item = new Item(
    id = rs.get(rn.id),
    itemName = rs.get(rn.itemName),
    unitPrice = rs.get(rn.unitPrice)
  )
}
