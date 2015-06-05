package chandu0101.scalajsreact.pages

import chandu0101.scalajsreact.components.InOutGenerator
import chandu0101.scalajsreact.util.Generator
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{BackendScope, ReactComponentB, ReactEventI, _}

import scalacss.Defaults._
import scalacss.ScalaCssReact._

object ReactJSWrapperPage {

  object Style extends StyleSheet.Inline {

    import dsl._

    val container = style(padding(30 px))

    val settings = style(display.flex,justifyContent.flexStart,
    padding(5 px),
    unsafeChild("div")(flex := "1"))
  }

  case class State(cName: String,withChildren : Boolean = true,jsComponentName : String = "")

  class Backend(t: BackendScope[_, State]) {

    def generate(in: String) = {
      Generator.generateWrapperClass(in, t.state.cName,t.state.jsComponentName, t.state.withChildren)
    }

    def handleCName(e: ReactEventI) = t.modState(_.copy(cName = e.target.value.trim))

    def handleJSComponentName(e: ReactEventI) = t.modState(_.copy(jsComponentName = e.target.value.trim))

    def handleWithChildren(e:ReactEventI) = {
      t.modState(s => s.copy(withChildren = !s.withChildren))
    }

  }

  val defaultProps =
    """
      |key: PropTypes.string,
      |style: PropTypes.js.Any,
      |ref: PropTypes.String
    """.stripMargin

  val generator = ReactComponentB[Unit]("ReactJSWrapper")
    .initialState(State(""))
    .backend(new Backend(_))
    .render((_, S, B) => {
    <.div(Style.container)(
      <.h3("Generate  scalajs-react Wrappers for ReactJS Components"),
      <.div(Style.settings,
        <.div(<.input(^.width := "200px" , ^.tpe := "text", ^.onChange ==> B.handleCName, ^.placeholder := "scala component name")),
        <.div(<.input(^.width := "250px" ,^.tpe := "text", ^.onChange ==> B.handleJSComponentName, ^.placeholder := "js component name")),
        <.div(
          <.input(^.tpe := "checkbox", ^.onChange ==> B.handleWithChildren, ^.checked := S.withChildren),
          <.span("With Children")
        )
      ),
      InOutGenerator(inputValue = defaultProps, onButtonClick = B.generate)
    )
  })
    .buildU

  def apply() = generator()
}
