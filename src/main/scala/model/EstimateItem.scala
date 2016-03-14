package model

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

case class EstimateItem(
  id: Int,
  estimateId: Long,
  sortNum: Long,
  itemName: Option[String] = None,
  unitPrice: Option[BigDecimal] = None,
  quantity: Option[Long] = None,
  total: Option[BigDecimal] = None,
  memo: Option[String] = None
)

object EstimateItem extends SkinnyCRUDMapperWithId[Int, EstimateItem] {
  override lazy val tableName = "estimate_item"
  override lazy val defaultAlias = createAlias("ei")
  override def idToRawValue(id: String): Any = id
  override def rawValueToId(value: Any): String = value.toString
  override def useExternalIdGenerator = true
  override def generateId = java.util.UUID.randomUUID.toString

  /*
   * If you're familiar with ScalikeJDBC/Skinny ORM, using #autoConstruct makes your mapper simpler.
   * (e.g.)
   * override def extract(rs: WrappedResultSet, rn: ResultName[EstimateItem]) = autoConstruct(rs, rn)
   *
   * Be aware of excluding associations like this:
   * (e.g.)
   * case class Member(id: Long, companyId: Long, company: Option[Company] = None)
   * object Member extends SkinnyCRUDMapper[Member] {
   *   override def extract(rs: WrappedResultSet, rn: ResultName[Member]) =
   *     autoConstruct(rs, rn, "company") // "company" will be skipped
   * }
   */
  override def extract(rs: WrappedResultSet, rn: ResultName[EstimateItem]): EstimateItem = new EstimateItem(
    id = rs.get(rn.id),
    estimateId = rs.get(rn.estimateId),
    sortNum = rs.get(rn.sortNum),
    itemName = rs.get(rn.itemName),
    unitPrice = rs.get(rn.unitPrice),
    quantity = rs.get(rn.quantity),
    total = rs.get(rn.total),
    memo = rs.get(rn.memo)
  )
}
