package model

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

/**
  * DIによるDao注入テスト用Entity.
  * @param id ID
  * @param staffName 社員名
  */
case class StaffDi(
  id: Long,
  staffName: String
)

/**
  * StaffDiのDao
  */
trait StaffDiDao {
  /**
    * 登録.
    * @param entity 対象Entity
    * @param session Session
    * @return 生成ID
    */
  def create(entity:StaffDi)(implicit session:DBSession):Long

  /***
    * 更新.
    * @param entity 対象Entity
    * @return ID
    */
  def update(entity:StaffDi)(implicit session:DBSession):Long

  /**
    * LIKE検索の検証用に使用します
    * @param staffName 検索文字列(前方一致)
    * @return 該当データ
    */
  def findByStaffName(staffName:String)(implicit session:DBSession):Seq[StaffDi]

  /**
    * IDによる検索.
    * @param id ID
    * @param s Session
    * @return 該当データ
    */
  def findById(id:Long)(implicit s:DBSession):Option[StaffDi]

  /**
    * IDによる削除.
    * @param id ID
    * @param s Session
    * @return 削除件数
    */
  def deleteById(id:Long)(implicit s:DBSession):Int
}

/**
  * 実装クラス.
  */
object StaffDi extends SkinnyCRUDMapper[StaffDi] with StaffDiDao {
  override lazy val tableName = "staff_di"
  override lazy val defaultAlias = createAlias("sd")

  override def extract(rs: WrappedResultSet, rn: ResultName[StaffDi]): StaffDi = new StaffDi(
    id = rs.get(rn.id),
    staffName = rs.get(rn.staffName)
  )

  /**
    * 登録.
    *
    * @param entity  対象Entity
    * @param session Session
    * @return 生成ID
    */
  override def create(entity: StaffDi)(implicit session: DBSession): Long = {
    StaffDi.createWithAttributes(
      'staffName -> entity.staffName
    )
  }

  /** *
    * 更新.
    *
    * @param entity 対象Entity
    * @return ID
    */
  override def update(entity: StaffDi)(implicit session: DBSession): Long = {
    StaffDi.updateById(entity.id).withAttributes(
      'staffName -> entity.staffName
    )
  }

  /**
    * LIKE検索の検証用に使用します
    *
    * @param staffName 検索文字列(前方一致)
    * @return 該当データ
    */
  override def findByStaffName(staffName: String)(implicit session: DBSession): Seq[StaffDi] = {
    val sd = StaffDi.syntax("sd")
    withSQL {
      select(sd.result.*)
        .from(StaffDi as sd)
        .where.like(sd.staffName, LikeConditionEscapeUtil.beginsWith(staffName))
        .orderBy(sd.id)
    }.map { rs =>
      StaffDi.extract(rs, sd.resultName)
    }.list.apply
  }
}
