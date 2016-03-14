package dao

import model.Staff

/**
  * StaffのDao.
  */
object StaffDao {

  /***
    * 登録.
    * @param entity 対象Entity
    * @return 生成ID
    */
  def create(entity:Staff):Long = {
    Staff.createWithAttributes(
      'staffName -> entity.staffName
    )
  }

  /***
    * 更新.
    * @param entity 対象Entity
    * @return ID
    */
  def update(entity:Staff):Long = {
    Staff.updateById(entity.id).withAttributes(
      'staffName -> entity.staffName
    )
  }

  /***
    * IDによる取得.
    * @param id ID
    * @return 該当データ(存在しない場合、None)
    */
  def findById(id:Long):Option[Staff] = {
    Staff.findById(id)
  }

  /***
    * IDによる削除.
    * @param id ID
    */
  def deleteById(id:Long) = {
    Staff.deleteById(id)
  }

}
