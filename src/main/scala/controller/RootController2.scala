package controller

import java.util.Date

import logic.StaffDiLogic
import scalikejdbc.DB
import util.BindModule

/**
  * DIを使用したLogicの呼び方
  */
class RootController2 extends ApplicationController {

  def index = {
    DB.localTx { implicit session =>
      val staffDiLogic = BindModule.injector.getInstance(classOf[StaffDiLogic])
      staffDiLogic.createStaffDi("DIも良いもんだぜ！" + new Date())
      render("/root/index")
    }
  }

}
