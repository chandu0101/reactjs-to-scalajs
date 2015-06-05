package chandu0101.scalajsreact

import chandu0101.scalajsreact.css.AppCSS
import chandu0101.scalajsreact.routing.AppRouter
import japgolly.scalajs.react._
import org.scalajs.dom

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport


object ReactApp extends JSApp {

  @JSExport
  override def main(): Unit = {
    AppCSS.load
    AppRouter.router() render dom.document.body
  }


}
