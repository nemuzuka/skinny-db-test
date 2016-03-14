package logic

import model.Staff
import scalikejdbc.{AutoSession, DBSession}

/**
  * staffに関するLogic.
  */
trait StaffLogic {

  /***
    * Staff登録.
    * @param name Staff名
    * @return 生成ID
    */
  def createStaff(name:String)(implicit session:DBSession):Long = {
    Staff.create(Staff(id = -1L, staffName = name))
  }

  /***
    * Staff取得.
    * @param id ID
    * @return 該当データ(存在しない場合、None)
    */
  def getStaff(id:Long)(implicit session:DBSession):Option[Staff] = {
    Staff.findById(id)
  }

}

object StaffLogic extends StaffLogic