package logic

import _root_.test.DBTestTrait
import com.google.inject.Inject
import org.scalatest._
import org.scalatest.fixture.FunSpec
import scalikejdbc.scalatest._
import skinny._
import _root_.util.BindModule
import scalikejdbc.DBSession

/**
  * StaffDiLogicで実際のDaoを使用するテスト.
  */
class StaffDiLogicImplSpec extends FunSpec with AutoRollback with Matchers with DBSettings with DBTestTrait with BeforeAndAfter {

  @Inject
  val staffDiLogic:StaffDiLogic = null

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

  /**
    * 実際のDaoを呼びDBアクセスするパターン
    */
  describe("getStaff") {
    it("DB取得") { implicit session =>
      val actualOpt = staffDiLogic.getStaffDi(-1L)
      actualOpt should be (None)

      staffDiLogic.getStaffDi(1L).get.staffName should be ("社員-あ")
    }
  }
}
