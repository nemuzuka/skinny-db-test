package logic

import com.google.inject.Inject
import model.{Staff, StaffDi, StaffDiDao}
import scalikejdbc.DBSession

/**
  * StaffDiのtrait.
  */
trait StaffDiLogic {

  /**
    * staffDi登録.
    * @param name 名称
    * @param session Session
    * @return 生成ID
    */
  def createStaffDi(name:String)(implicit session:DBSession):Long

  /***
    * Staff取得.
    * @param id ID
    * @return 該当データ(存在しない場合、None)
    */
  def getStaffDi(id:Long)(implicit session:DBSession):Option[StaffDi]
}

/**
  * 実装クラス.
  */
class StaffDiLogicImpl extends StaffDiLogic {

  @Inject
  val staffDiDao:StaffDiDao = null

  /** ${inheritDoc} */
  override def createStaffDi(name:String)(implicit session:DBSession):Long = {
    staffDiDao.create(StaffDi(id = -1L, staffName = name))
  }

  /** ${inheritDoc} */
  override def getStaffDi(id:Long)(implicit session:DBSession):Option[StaffDi] = {
    staffDiDao.findById(id)
  }

}