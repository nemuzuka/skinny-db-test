package controller

import java.util.Date

import logic.StaffLogic
import scalikejdbc.DB
import skinny._

class RootController extends ApplicationController {

  def index = {
    DB.localTx { implicit session =>
      StaffLogic.createStaff("早乙女光伝説" + new Date())
      render("/root/index")
    }
  }

}
