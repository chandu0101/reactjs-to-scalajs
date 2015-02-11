package chandu0101.scalajsreact.pages

import chandu0101.scalajsreact.components.InOutGenerator
import chandu0101.scalajsreact.util.Generator
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.all._

import scala.collection.mutable
import scala.scalajs.js

/**
 * Created by chandrasekharkode .
 */
object CssToJSGenerator {


  case class State(autoPrefixer: Boolean = false)

  class Backend(t: BackendScope[_, State]) {

    def generate(in: String) = {
      Generator.generateJsStyles(in,t.state.autoPrefixer)
    }

    def onCheckBoxChange = {
      t.modState(s => s.copy(autoPrefixer = !s.autoPrefixer))
    }
  }


  val generator = ReactComponentB[Unit]("CSSToJSGenerator")
    .initialState(State())
    .backend(new Backend(_))
    .render((_, S, B) => {
    div(padding := "30px")(
      h3("Convert Css To Js styles "),
      input(tpe := "checkbox", checked := S.autoPrefixer ,onChange --> B.onCheckBoxChange )("Use Auto Prefixer"),
      InOutGenerator(onButtonClick = B.generate)
    )
  })
    .buildU


  def apply() = generator()

}
