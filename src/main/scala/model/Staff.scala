package model

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

case class Staff(
  id: Long,
  staffName: String
)

object Staff extends SkinnyCRUDMapper[Staff] {
  override lazy val tableName = "staff"
  override lazy val defaultAlias = createAlias("s")

  override def useExternalIdGenerator = true
  override def generateId:Long = DB localTx {
    implicit session =>
      //シーケンステーブルからIDを採番して、IDを設定する
      sql"select nextval('staff_seq') as id".map(_.long("id")).first().apply.get
  }

  /*
   * If you're familiar with ScalikeJDBC/Skinny ORM, using #autoConstruct makes your mapper simpler.
   * (e.g.)
   * override def extract(rs: WrappedResultSet, rn: ResultName[Staff]) = autoConstruct(rs, rn)
   *
   * Be aware of excluding associations like this:
   * (e.g.)
   * case class Member(id: Long, companyId: Long, company: Option[Company] = None)
   * object Member extends SkinnyCRUDMapper[Member] {
   *   override def extract(rs: WrappedResultSet, rn: ResultName[Member]) =
   *     autoConstruct(rs, rn, "company") // "company" will be skipped
   * }
   */
  override def extract(rs: WrappedResultSet, rn: ResultName[Staff]): Staff = new Staff(
    id = rs.get(rn.id),
    staffName = rs.get(rn.staffName)
  )
}
