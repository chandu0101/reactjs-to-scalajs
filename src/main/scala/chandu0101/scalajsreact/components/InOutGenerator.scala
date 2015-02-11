package chandu0101.scalajsreact.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.all._

import chandu0101.scalajs.react.components.all._
import scala.scalajs.js


/**
 * Created by chandrasekharkode .
 */
object InOutGenerator {

  val display = "display".reactStyle
  val flexDirection = "flexDirection".reactStyle
  val margin = "margin".reactStyle
  val msFlexDirection = "msFlexDirection".reactStyle
  val webkitBoxDirection = "webkitBoxDirection".reactStyle
  val webkitBoxOrient = "webkitBoxOrient".reactStyle
  val webkitFlexDirection = "webkitFlexDirection".reactStyle
  val alignSelf = "alignSelf".reactStyle
  val msFlexItemAlign = "msFlexItemAlign".reactStyle
  val webkitAlignSelf = "webkitAlignSelf".reactStyle
  object Style {

    val box = Seq(
      display := "-ms-flexbox" ,
    display := "-webkit-box" ,
    display := "-webkit-flex" ,
    display := "flex" ,
    boxShadow := "0 1px 4px lightBlue",
    flexDirection := "column" ,
    msFlexDirection := "column" ,
    alignItems := "stretch",
    webkitBoxDirection := "normal" ,
    webkitBoxOrient := "vertical" ,
    webkitFlexDirection := "column")

    val item = Seq(margin := "20px", fontSize := "13px",textAlign := "center"
    )

    val button = Seq(backgroundColor := "#1189D2" ,
      border := "1px solid transparent" ,
      boxShadow := "0 4px 8px 0 rgba(0, 0, 0, 0.12), 0 1px 2px 0 rgba(0, 0, 0, 0.24)" ,
      color := "#ffffff" ,
      cursor := "pointer" ,
      textDecoration := "none",
      display := "inline-block" ,
      fontWeight := "600" ,
      padding := "10px 18px" ,
      fontSize := "13px",
      borderRadius := "2px" ,
      webkitBoxShadow := "0 1px 3px 0 rgba(0, 0, 0, 0.12), 0 1px 2px 0 rgba(0, 0, 0, 0.24)", alignSelf := "center" ,
    msFlexItemAlign := "center" ,
    webkitAlignSelf := "center"
    )

  }

  case class State(inputValue: String ,outputValue : String = "")

  class Backend(t: BackendScope[Props, State]) {

    def onInputChange(e : ReactEventI) = {
      t.modState(_.copy(inputValue = e.target.value))
    }

    def onButtonClick = {
      val result = t.props.onButtonClick(t.state.inputValue)
      t.modState(_.copy(outputValue = result))
    }

  }

  val component = ReactComponentB[Props]("InOutGenerator")
    .initialStateP(p => State(p.inputValue))
    .backend(new Backend(_))
    .render((P, S, B) => {
       div(Style.box)(
         textarea(Style.item ,minHeight := "300px", paddingLeft := "25px", value := S.inputValue, onChange ==> B.onInputChange),
         a(Style.item,Style.button, onClick --> B.onButtonClick)("Generate"),
         textarea(Style.item, minHeight := "300px",paddingLeft := "25px", value := S.outputValue, readOnly := true)
       )
    })
    .build

  case class Props(inputValue : String,onButtonClick : String => String)

  def apply(inputValue : String = "",onButtonClick : String => String,ref: js.UndefOr[String] = "", key: js.Any = {}) = component.set(key, ref)(Props(inputValue,onButtonClick))
}
