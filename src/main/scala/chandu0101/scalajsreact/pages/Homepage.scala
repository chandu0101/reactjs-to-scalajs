package chandu0101.scalajsreact.pages

import chandu0101.scalajsreact.components.InOutGenerator
import chandu0101.scalajsreact.util.Generator
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.all._

/**
 * Created by chandrasekharkode on 12/9/14.
 */
object Homepage {

  //  val alignItems = "alignItems".reactStyle
  object Style {

    val menuWrapper = Seq(display := "flex",
      margin := "0 5rem",
      color := "rgb(244, 233, 233)")

  }

  case class State(gName: String, cName: String, rProps: String, sCode: String)

  class Backend(t: BackendScope[_, State]) {

    def generate(in: String) = {
      Generator.generateCode(t.state.gName, t.state.cName, in)
    }


  }


  val defaultProps =
    """
      |clsNames: React.PropTypes.css,
      |key: React.PropTypes.key,
      |ref: React.PropTypes.ref,
    """.stripMargin

  val generator = ReactComponentB[Unit]("HomePage")
    .initialState(State("", "", defaultProps, ""))
    .backend(new Backend(_))
    .render((_, S, B) => {
    div(padding := "30px")(
        h3("Genarate Scala Props from ReactJSProps"),
        InOutGenerator(inputValue = defaultProps, onButtonClick = B.generate)
      )
  })
    .buildU

  def apply() = generator()
}
