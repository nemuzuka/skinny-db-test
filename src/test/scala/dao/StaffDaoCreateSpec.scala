package dao

import model.Staff
import org.scalatest._
import org.scalatest.fixture.FunSpec
import skinny._
import scalikejdbc.scalatest._
/**
  * StaffDaoに対するcreateに関するテスト.
  */
class StaffDaoCreateSpec extends FunSpec with AutoRollback with Matchers with DBSettings {

  describe("create") {
    it("登録できることの確認") { implicit session =>
      val entity = Staff(id = -1L, staffName = "ほげほげ")
      val actualId = StaffDao.create(entity)
      actualId should be > 0L
    }
  }
}
