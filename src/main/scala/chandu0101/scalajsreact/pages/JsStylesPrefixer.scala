package chandu0101.scalajsreact.pages

import chandu0101.scalajsreact.components.InOutGenerator
import chandu0101.scalajsreact.util.Generator
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.all._



/**
 * Created by chandrasekharkode .
 */
object JsStylesPrefixer {

  case class State(checked: Boolean)

  class Backend(t: BackendScope[_, State]) {


    def applyPrefixes(in : String) = {
      Generator.autoPrefixJsStyles(in)
    }

  }

  val component = ReactComponentB[Unit]("JsStylesPrefixer")
    .initialState(State(false))
    .backend(new Backend(_))
    .render((P, S, B) => {
      div(padding := "30px")(
       h3("AutoPrefixer for scalajs styles"),
      InOutGenerator(onButtonClick = B.applyPrefixes,inputValue = "display := flex,")
      )
    })
    .buildU


  def apply() = component()

}
