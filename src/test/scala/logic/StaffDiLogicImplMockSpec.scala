package logic

import _root_.test.DBTestTrait
import _root_.util.BindModule
import com.google.inject._
import com.google.inject.util._
import model.{StaffDi, StaffDiDao}
import org.scalatest._
import org.scalatest.fixture.FunSpec
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import scalikejdbc.DBSession
import scalikejdbc.scalatest._
import skinny._

/**
  * StaffDiLogicでMockのDaoを使用するテスト.
  */
class StaffDiLogicImplMockSpec extends FunSpec with AutoRollback with Matchers with DBSettings with DBTestTrait with BeforeAndAfter with MockitoSugar {

  @Inject
  val staffDiLogic:StaffDiLogic = null

  /***
    * テスト用データ登録.
    * @param session DBSession
    */
  override def fixture(implicit session: DBSession) {
    importExcelData(session, Seq("model/StaffDiFixture.xlsx"))
  }

  /**
    * MockのDaoを呼ぶパターン
    */
  describe("getStaffDi") {
    it("DB取得") { implicit session =>

      val m = mock[StaffDiDao]
      when(m.findById(1L)(session)).thenReturn(None)
      when(m.findById(2L)(session)).thenReturn(Option(
        StaffDi(id=2L, staffName="Mock社員")
      ))

      //差し替えたMockで設定する
      Guice.createInjector(Modules.`override`(new BindModule()).`with`(getTestModule(m))).injectMembers(this)

      staffDiLogic.getStaffDi(1L) should be (None)
      val actual = staffDiLogic.getStaffDi(2L).get
      actual.id should be (2L)
      actual.staffName should be ("Mock社員")
    }
  }

  /**
    * DI定義上書き
    * @return Module
    */
  private def getTestModule(m:StaffDiDao) = new AbstractModule() {
    override def configure() = {
      bind(classOf[StaffDiDao]).toInstance(m)
    }
  }

}
