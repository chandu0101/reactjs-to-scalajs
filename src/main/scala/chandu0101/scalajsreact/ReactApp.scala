package chandu0101.scalajsreact

import chandu0101.scalajsreact.routing.AppRouter
import japgolly.scalajs.react._
import org.scalajs.dom

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport


/**
 * Created by chandrasekharkode on 11/14/14.
 */


object ReactApp extends JSApp {

  @JSExport
  override def main(): Unit = {
    React.render(AppRouter.C(), dom.document.getElementById("container"))
  }


}
