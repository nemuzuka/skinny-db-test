package model

import _root_.test.DBTestTrait
import org.scalatest._
import org.scalatest.fixture.FunSpec
import scalikejdbc.DBSession
import scalikejdbc.scalatest._
import skinny._

/***
  * Staffに関するテスト.
  */
class StaffSpec extends FunSpec with AutoRollback with Matchers with DBSettings with DBTestTrait {

  /***
    * テスト用データ登録.
    * @param session DBSession
    */
  override def fixture(implicit session: DBSession) {
    importExcelData(session, Seq("model/StaffFixture.xlsx"))
  }

  describe("update") {
    it("id=1の社員名を変更するテスト") { implicit session =>
      Staff.findById(1L).get.staffName should be ("社員1")

      val staff = Staff(id=1L, staffName = "更新後社員名！！")
      Staff.update(staff)

      Staff.findById(1L).get.staffName should be ("更新後社員名！！")
    }
  }

  describe("findById") {
    it("id=1の社員を取得するテスト") { implicit session =>
      val actualOpt = Staff.findById(1L)
      actualOpt should not be None
      actualOpt.get.staffName should be ("社員1")
    }
  }

  describe("deleteById") {
    it("id=1の社員を削除するテスト") { implicit session =>
      Staff.findById(1L) should not be None
      Staff.deleteById(1L)
      Staff.findById(1L) should be (None)
    }
  }

}
