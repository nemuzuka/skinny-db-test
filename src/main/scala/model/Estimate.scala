package model

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

case class Estimate(
  id: Long,
  estimateTitle: Option[String] = None,
  taxRate: Option[BigDecimal] = None,
  subtotal: Option[BigDecimal] = None,
  tax: Option[BigDecimal] = None,
  total: Option[BigDecimal] = None,
  estimateDate: Option[LocalDate] = None,
  createStaffId: Option[Long] = None,
  createAt: Option[DateTime] = None,
  lastUpdateStaffId: Option[Long] = None,
  lastUpdateAt: Option[DateTime] = None
)

object Estimate extends SkinnyCRUDMapper[Estimate] {
  override lazy val tableName = "estimate"
  override lazy val defaultAlias = createAlias("e")

  /*
   * If you're familiar with ScalikeJDBC/Skinny ORM, using #autoConstruct makes your mapper simpler.
   * (e.g.)
   * override def extract(rs: WrappedResultSet, rn: ResultName[Estimate]) = autoConstruct(rs, rn)
   *
   * Be aware of excluding associations like this:
   * (e.g.)
   * case class Member(id: Long, companyId: Long, company: Option[Company] = None)
   * object Member extends SkinnyCRUDMapper[Member] {
   *   override def extract(rs: WrappedResultSet, rn: ResultName[Member]) =
   *     autoConstruct(rs, rn, "company") // "company" will be skipped
   * }
   */
  override def extract(rs: WrappedResultSet, rn: ResultName[Estimate]): Estimate = new Estimate(
    id = rs.get(rn.id),
    estimateTitle = rs.get(rn.estimateTitle),
    taxRate = rs.get(rn.taxRate),
    subtotal = rs.get(rn.subtotal),
    tax = rs.get(rn.tax),
    total = rs.get(rn.total),
    estimateDate = rs.get(rn.estimateDate),
    createStaffId = rs.get(rn.createStaffId),
    createAt = rs.get(rn.createAt),
    lastUpdateStaffId = rs.get(rn.lastUpdateStaffId),
    lastUpdateAt = rs.get(rn.lastUpdateAt)
  )
}
