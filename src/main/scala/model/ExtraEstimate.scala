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
    * @param estimateDateFrom 見積日付From
    * @param estimateDateTo 見積日付To
    * @param itemName 商品名(前方一致)
    */
  case class Condition(
    staffIds:Option[Seq[Long]],
    totalFrom:Option[BigDecimal],
    totalTo:Option[BigDecimal],
    itemIds:Option[Seq[Long]],
    estimateDateFrom:Option[LocalDate],
    estimateDateTo:Option[LocalDate],
    itemName:Option[String]
  )

  /***
    * 検索条件に合致する見積一覧取得.
    * @param condition 検索条件
    */
  def findByCondition(condition:Condition)(implicit session:DBSession):List[Estimate] = {
    val e = Estimate.syntax("e")
    val ei = EstimateItem.syntax("ei")
    withSQL {
      select(e.result.*)
        .from(Estimate as e)
        .where(sqls.toAndConditionOpt(
          condition.staffIds.map(staffIds => sqls.in(e.lastUpdateStaffId, staffIds)),
          condition.estimateDateFrom.map(estimateDateFrom => sqls.ge(e.estimateDate, estimateDateFrom)),
          condition.estimateDateTo.map(estimateDateTo => sqls.le(e.estimateDate, estimateDateTo)),
          condition.totalFrom.map(totalFrom => sqls.ge(e.total, totalFrom)),
          condition.totalTo.map(totalTo => sqls.le(e.total, totalTo)),
          condition.itemIds.map(itemIds => sqls.exists(
            sqls"""
                  select
                    ${ei.id}
                  from
                    ${EstimateItem as ei}
                  where
                    ${ei.estimateId} = ${e.id} and
                    ${ei.itemId} in (${itemIds})
              """
          )),
          condition.itemName.map(itemName => sqls.exists(
            sqls"""
                  select
                    ${ei.id}
                  from
                    ${EstimateItem as ei}
                  where
                    ${ei.estimateId} = ${e.id} and
                    ${ei.itemName} like ${LikeConditionEscapeUtil.beginsWith(itemName)}
              """
          ))
        ))
        .orderBy(e.estimateDate)
    }.map { rs =>
      Estimate.extract(rs, e.resultName)
    }.list.apply
  }
}

