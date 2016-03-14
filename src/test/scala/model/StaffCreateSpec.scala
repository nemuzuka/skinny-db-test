package model

import org.scalatest._
import org.scalatest.fixture.FunSpec
import scalikejdbc.scalatest._
import skinny._

/**
  * Staffに対するcreateに関するテスト.
  */
class StaffCreateSpec extends FunSpec with AutoRollback with Matchers with DBSettings {

  describe("create") {
    it("登録できることの確認") { implicit session =>
      val entity = Staff(id = -1L, staffName = "ほげほげ")
      val actualId = Staff.create(entity)
      actualId should be > 0L
    }
  }
}
