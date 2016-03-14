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
  staffId: Option[Long] = None
)

object Estimate extends SkinnyCRUDMapper[Estimate] {
  override lazy val tableName = "estimate"
  override lazy val defaultAlias = createAlias("e")
  override def useExternalIdGenerator = true
  override def generateId:Long = DB localTx {
    implicit session =>
      //シーケンステーブルからIDを採番して、IDを設定する
      sql"select nextval('estimate_seq') as id".map(_.long("id")).first().apply.get
  }

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
    staffId = rs.get(rn.staffId)
  )
}
