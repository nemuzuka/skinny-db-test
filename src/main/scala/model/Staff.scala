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

  /***
    * 登録.
    * @param entity 対象Entity
    * @return 生成ID
    */
  def create(entity:Staff)(implicit session:DBSession):Long = {
    Staff.createWithAttributes(
      'staffName -> entity.staffName
    )
  }

  /***
    * 更新.
    * @param entity 対象Entity
    * @return ID
    */
  def update(entity:Staff)(implicit session:DBSession):Long = {
    Staff.updateById(entity.id).withAttributes(
      'staffName -> entity.staffName
    )
  }

  /**
    * LIKE検索の検証用に使用します
    * @param staffName 検索文字列(前方一致)
    */
  def findByStaffName(staffName:String)(implicit session:DBSession) = {
    val s = Staff.syntax("s")
    withSQL {
      select(s.result.*)
        .from(Staff as s)
        .where.like(s.staffName, LikeConditionEscapeUtil.beginsWith(staffName))
        .orderBy(s.id)
    }.map { rs =>
      Staff.extract(rs, s.resultName)
    }.list.apply
  }
}
