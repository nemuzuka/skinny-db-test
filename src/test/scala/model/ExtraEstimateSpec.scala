package model

import _root_.test.DBTestTrait
import model.ExtraEstimate.Condition
import org.joda.time.format.DateTimeFormat
import org.scalatest._
import org.scalatest.fixture.FunSpec
import scalikejdbc.DBSession
import scalikejdbc.scalatest._
import skinny._

/***
  * 見積検索に関するテスト.
  */
class ExtraEstimateSpec extends FunSpec with AutoRollback with Matchers with DBSettings with DBTestTrait {

  /***
    * テスト用データ登録.
    * @param session DBSession
    */
  override def fixture(implicit session: DBSession) {
    importExcelData(session, Seq("model/StaffFixture.xlsx", "model/ItemFixture.xlsx", "model/ExtraEstimateFixture.xlsx"))
  }

  describe("findByCondition") {
    it("検索条件が何も設定されていない時のテスト") { implicit session =>
      val condition = Condition(staffIds=None, totalFrom=None, totalTo = None, itemIds=None, estimateDateFrom=None, estimateDateTo=None, itemName=None)
      val actualList = ExtraEstimate.findByCondition(condition)
      actualList.size should be (4)
      actualList.head.id should be (100L)
      actualList(1).id should be (101L)
      actualList(2).id should be (102L)
      actualList(3).id should be (103L)
    }

    it("見積日付from指定時のテスト") { implicit session =>
      val condition = Condition(staffIds=None, totalFrom=None, totalTo = None, itemIds=None,
        estimateDateFrom=Option(DateTimeFormat.forPattern("yyyy-MM-dd").parseLocalDate("2016-01-05")), estimateDateTo=None, itemName=None)
      val actualList = ExtraEstimate.findByCondition(condition)
      actualList.size should be (2)
      actualList.head.id should be (102L)
      actualList(1).id should be (103L)
    }

    it("見積日付to指定時のテスト") { implicit session =>
      val condition = Condition(staffIds=None, totalFrom=None, totalTo = None, itemIds=None,
        estimateDateFrom=None, estimateDateTo=Option(DateTimeFormat.forPattern("yyyy-MM-dd").parseLocalDate("2016-01-04")), itemName=None)
      val actualList = ExtraEstimate.findByCondition(condition)
      actualList.size should be (2)
      actualList.head.id should be (100L)
      actualList(1).id should be (101L)
    }

    it("合計from指定時のテスト") { implicit session =>
      val condition = Condition(staffIds=None, totalFrom=Option(BigDecimal(212)), totalTo = None, itemIds=None, estimateDateFrom=None, estimateDateTo=None, itemName=None)
      val actualList = ExtraEstimate.findByCondition(condition)
      actualList.size should be (2)
      actualList.head.id should be (102L)
      actualList(1).id should be (103L)
    }

    it("合計to指定時のテスト") { implicit session =>
      val condition = Condition(staffIds=None, totalFrom=None, totalTo = Option(BigDecimal(211)), itemIds=None, estimateDateFrom=None, estimateDateTo=None, itemName=None)
      val actualList = ExtraEstimate.findByCondition(condition)
      actualList.size should be (2)
      actualList.head.id should be (100L)
      actualList(1).id should be (101L)
    }

    it("社員ID指定時のテスト 1件指定") { implicit session =>
      //1件指定
      val condition = Condition(staffIds=Option(Seq(3)), totalFrom=None, totalTo = None, itemIds=None, estimateDateFrom=None, estimateDateTo=None, itemName=None)
      val actualList = ExtraEstimate.findByCondition(condition)
      actualList.size should be (2)
      actualList.head.id should be (100L)
      actualList(1).id should be (102L)
    }

    it("社員ID指定時のテスト 2件指定") { implicit session =>
      val condition = Condition(staffIds=Option(Seq(4,3)), totalFrom=None, totalTo = None, itemIds=None, estimateDateFrom=None, estimateDateTo=None, itemName=None)
      val actualList = ExtraEstimate.findByCondition(condition)
      actualList.size should be (4)
      actualList.head.id should be (100L)
      actualList(1).id should be (101L)
      actualList(2).id should be (102L)
      actualList(3).id should be (103L)
    }

    it("社員ID指定時のテスト 該当データ無し") { implicit session =>
      val condition = Condition(staffIds=Option(Seq(10)), totalFrom=None, totalTo = None, itemIds=None, estimateDateFrom=None, estimateDateTo=None, itemName=None)
      val actualList = ExtraEstimate.findByCondition(condition)
      actualList.size should be (0)
    }

    it("商品ID指定時のテスト 2件指定") { implicit session =>
      val condition = Condition(staffIds=None, totalFrom=None, totalTo = None, itemIds=Option(Seq(11L,13L)), estimateDateFrom=None, estimateDateTo=None, itemName=None)
      val actualList = ExtraEstimate.findByCondition(condition)
      actualList.size should be (3)
      actualList.head.id should be (100L)
      actualList(1).id should be (101L)
      actualList(2).id should be (103L)
    }
    it("商品ID指定時のテスト 1件指定") { implicit session =>
      val condition = Condition(staffIds=None, totalFrom=None, totalTo = None, itemIds=Option(Seq(13L)), estimateDateFrom=None, estimateDateTo=None, itemName=None)
      val actualList = ExtraEstimate.findByCondition(condition)
      actualList.size should be (2)
      actualList.head.id should be (101L)
      actualList(1).id should be (103L)
    }
    it("商品ID指定時のテスト 該当データ無し") { implicit session =>
      val condition = Condition(staffIds=None, totalFrom=None, totalTo = None, itemIds=Option(Seq(20L)), estimateDateFrom=None, estimateDateTo=None, itemName=None)
      val actualList = ExtraEstimate.findByCondition(condition)
      actualList.size should be (0)
    }

    it("名称指定時のテスト 完全一致するものがある") { implicit session =>
      val condition = Condition(staffIds=None, totalFrom=None, totalTo = None, itemIds=None, estimateDateFrom=None, estimateDateTo=None, itemName=Option("名称1"))
      val actualList = ExtraEstimate.findByCondition(condition)
      actualList.size should be (1)
      actualList.head.id should be (100L)
    }

    it("名称指定時のテスト 前方一致") { implicit session =>
      val condition = Condition(staffIds=None, totalFrom=None, totalTo = None, itemIds=None, estimateDateFrom=None, estimateDateTo=None, itemName=Option("名称"))
      val actualList = ExtraEstimate.findByCondition(condition)
      actualList.size should be (3)
      actualList.head.id should be (100L)
      actualList(1).id should be (101L)
      actualList(2).id should be (103L)
    }

    it("組み合わせのテスト 複数の組み合わせでSQLエラーが発生しないこと") { implicit session =>
      val condition = Condition(staffIds=None, totalFrom=Option(BigDecimal(1)), totalTo = None, itemIds=Option(Seq(10L)), estimateDateFrom=None, estimateDateTo=None, itemName=Option("名称"))
      val actualList = ExtraEstimate.findByCondition(condition)
      actualList.size should be (2)
      actualList.head.id should be (100L)
      actualList(1).id should be (103L)
    }
  }
}
