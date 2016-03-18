package model

import org.scalatest._
import scalikejdbc.scalatest._
import skinny._

/**
  * Staffに対するcreateに関するテスト.
  * シーケンスでidを採番する為、事前データを入れると一意制約エラーになる可能性があるので、
  * 事前データは入れません
  */
class StaffCreateSpec extends fixture.FunSpec with AutoRollback with Matchers with DBSettings {

  describe("create") {
    it("登録できることの確認") { implicit session =>
      Staff.findAll().size should be (0)

      val entity = Staff(id = -1L, staffName = "ほげほげ")
      val actualId = Staff.create(entity)
      actualId should be > 0L
    }
  }
}
