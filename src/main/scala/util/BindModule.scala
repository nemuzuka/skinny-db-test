package util

import com.google.inject.{AbstractModule, Guice, Singleton}
import logic.{StaffDiLogic, StaffDiLogicImpl}
import model.{StaffDi, StaffDiDao}

/**
  * DI設定Module.
  */
class BindModule extends AbstractModule{
  /**
    * DI設定定義
    */
  override def configure() = {

    //Logicはclassなのでto
    bind(classOf[StaffDiLogic]).to(classOf[StaffDiLogicImpl]).in(classOf[Singleton])

    //Daoは、objectなので、インスタンスを設定
    bind(classOf[StaffDiDao]).toInstance(StaffDi)
  }
}

/**
  * DI設定インスタンス管理.
  */
object BindModule {
  val injector = Guice.createInjector(new BindModule())
}
