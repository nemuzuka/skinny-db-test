package model

import _root_.test.DBTestTrait
import com.google.inject.Inject
import org.scalatest._
import scalikejdbc.DBSession
import scalikejdbc.scalatest._
import skinny._
import _root_.util.BindModule

/***
  * DIを使用したDaoのテスト.
  * 本物のインスタンスを使用するパターン
  */
class StaffDiSpec extends fixture.FunSpec with AutoRollback with Matchers with DBSettings with DBTestTrait with BeforeAndAfter {

  @Inject
  val staffDiDao:StaffDiDao = null

  before {
    //@Injectアノテーションが付与されているtraitに対してインスタンス割当
    BindModule.injector.injectMembers(this)
  }

  /***
    * テスト用データ登録.
    * @param session DBSession
    */
  override def fixture(implicit session: DBSession) {
    importExcelData(session, Seq("model/StaffDiFixture.xlsx"))
  }

  describe("update") {
    it("id=1の社員名を変更するテスト") { implicit session =>
      staffDiDao.findById(1L).get.staffName should be ("社員-あ")

      val staffDi = StaffDi(id=1L, staffName = "更新後社員名！！")
      staffDiDao.update(staffDi)

      staffDiDao.findById(1L).get.staffName should be ("更新後社員名！！")
    }
  }

  describe("findById") {
    it("id=1の社員を取得するテスト") { implicit session =>
      val actualOpt = staffDiDao.findById(1L)
      actualOpt should not be None
      actualOpt.get.staffName should be ("社員-あ")
    }
  }

  describe("deleteById") {
    it("id=1の社員を削除するテスト") { implicit session =>
      staffDiDao.findById(1L) should not be None
      staffDiDao.deleteById(1L)
      staffDiDao.findById(1L) should be (None)
    }
  }

}
