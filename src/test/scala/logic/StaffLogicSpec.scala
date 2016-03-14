package logic

import org.scalatest._
import org.scalatest.fixture.FunSpec
import scalikejdbc.DBSession
import scalikejdbc.scalatest._
import skinny._

/**
  * StaffLogicの登録処理に対するテスト.
  */
class StaffLogicCreateSpec extends FunSpec with AutoRollback with Matchers with DBSettings {

  describe("createStaff & getStaff") {
    it("登録できること、DBから取得した際の確認") { implicit session =>
      //この単位でトランザクションが有効になる(抜けるとrollback)
      val id = StaffLogic.createStaff("つくったぜ！")
      id should be > 0L

      val actualOpt = StaffLogic.getStaff(id)
      actualOpt should not be None

      val actual = actualOpt.get
      actual.id should be (id)
      actual.staffName should be ("つくったぜ！")
    }
  }

  describe("getStaff") {
    it("該当データ無し") { implicit session =>
      val actualOpt = StaffLogic.getStaff(-1L)
      actualOpt should be (None)
    }
  }
}
