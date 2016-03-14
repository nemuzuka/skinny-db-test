package model

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

/***
  * ダミー用case class.
  * @param id ID
  */
case class ExtraEstimate(id:Long)

/***
  * 検索条件による見積検索.
  */
object ExtraEstimate extends SkinnyCRUDMapper[ExtraEstimate] {
  override lazy val tableName = "dummy"
  override lazy val defaultAlias = createAlias("dummy")

  override def extract(rs: WrappedResultSet, rn: ResultName[ExtraEstimate]): ExtraEstimate = new ExtraEstimate(
    id = rs.get(rn.id)
  )

  /***
    * 検索条件.
    * @param staffIds 社員IDSeq
    * @param totalFrom 合計From
    * @param totalTo 合計To
    * @param itemIds 商品IDSeq
    */
  case class Condition(
    staffIds:Option[Seq[Long]],
    totalFrom:Option[BigDecimal],
    totalTo:Option[BigDecimal],
    itemIds:Option[Seq[Long]]
  )

  /***
    * 検索条件に合致する見積一覧取得.
    * @param condition 検索条件
    */
  def findByCondition(condition:Condition) = {

  }

}

