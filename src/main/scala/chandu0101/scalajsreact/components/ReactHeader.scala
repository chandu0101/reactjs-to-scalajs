package chandu0101.scalajsreact.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.all._

import scala.scalajs.js


/**
 * Created by chandrasekharkode .
 */
object ReactHeader {


  trait Style {
    val headerStyle = Seq( background := "#F2706D" ,
      fontSize := "1.5em" ,
      height := "64px" ,
      lineHeight := "64px" ,
      position := "fixed" ,
      width := "100%" ,
      zIndex := "100")
  }

  val component = ReactComponentB[Props]("ReactHeader")
    .render((P,C) => {
      header(P.style.headerStyle ,C)
    })
    .build

  case class Props(style : Style)

  def apply(style : Style = new Style {},ref: js.UndefOr[String] = "", key: js.Any = {})(children : ReactNode*) = component.set(key, ref)(Props(style))

}
