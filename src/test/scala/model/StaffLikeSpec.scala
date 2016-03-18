package model

import _root_.test.DBTestTrait
import org.scalatest._
import scalikejdbc.DBSession
import scalikejdbc.scalatest._
import skinny._

/***
  * Like検索に関するテスト.
  */
class StaffLikeSpec extends fixture.FunSpec with AutoRollback with Matchers with DBSettings with DBTestTrait {

  /***
    * テスト用データ登録.
    * @param session DBSession
    */
  override def fixture(implicit session: DBSession) {
    importExcelData(session, Seq("model/StaffLikeFixture.xlsx"))
  }

  describe("findByStaffName") {
    it("山田") { implicit session =>
      val actual = Staff.findByStaffName("山田")
      actual.size should be (4)
      actual.head.id should be (1L)
      actual(1).id should be (2L)
      actual(2).id should be (3L)
      actual(3).id should be (4L)
    }

    it("片桐%") { implicit session =>
      val actual = Staff.findByStaffName("片桐%")
      actual.size should be (2)
      actual.head.id should be (8L)
      actual(1).id should be (9L)
    }

    it("片桐％") { implicit session =>
      val actual = Staff.findByStaffName("片桐％")
      actual.size should be (2)
      actual.head.id should be (10L)
      actual(1).id should be (11L)
    }

    //windowsだとエラーになる？
    it("片桐¥") { implicit session =>
      val actual = Staff.findByStaffName("片桐¥")
      actual.size should be (2)
      actual.head.id should be (12L)
      actual(1).id should be (13L)
    }

    it("片桐￥") { implicit session =>
      val actual = Staff.findByStaffName("片桐￥")
      actual.size should be (2)
      actual.head.id should be (14L)
      actual(1).id should be (15L)
    }

    it("片桐_") { implicit session =>
      val actual = Staff.findByStaffName("片桐_")
      actual.size should be (2)
      actual.head.id should be (16L)
      actual(1).id should be (17L)
    }

    it("片桐＿") { implicit session =>
      val actual = Staff.findByStaffName("片桐＿")
      actual.size should be (2)
      actual.head.id should be (18L)
      actual(1).id should be (19L)
    }

    //windowsだとエラーになる？
    it("片桐\\") { implicit session =>
      val actual = Staff.findByStaffName("片桐\\")
      actual.size should be (2)
      actual.head.id should be (20L)
      actual(1).id should be (21L)
    }

  }
}
