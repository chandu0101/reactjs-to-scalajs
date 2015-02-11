package chandu0101.scalajsreact.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.all._
import chandu0101.scalajs.react.components.all._

import scala.scalajs.js
/**
 * Created by chandrasekharkode .
 */
object ReactNavMenu {



  trait Style {

    val headerStyle = Seq( background := "#F2706D" ,
      fontSize := "1.5em" ,
      padding := 0,
    margin := 0,
      width := "100%" ,
      zIndex := "100")

    val menuNav = Seq( alignItems := "center" ,
      color := "rgb(244, 233, 233)" ,
      display := "-ms-flexbox" ,
      display := "-webkit-box" ,
      display := "-webkit-flex" ,
      display := "flex" ,
      margin := "0 3rem" ,
      msFlexAlign := "center" ,
      webkitAlignItems := "center" ,
      height := "64px" ,
      lineHeight := "64px" ,
      webkitBoxAlign := "center" )

    val menuItem = Seq(
      padding := "20px",
        color := "rgb(244, 233, 233)" ,
      textDecoration := "none" )

    val menuItemHover = Seq(background := "#f1453e")

  }

  val component = ReactComponentB[Props]("ReactNavMenu")
    .render((P) => {
       val menuNames = P.menu.keys
       header(P.style.headerStyle,
       nav(P.style.menuNav,
        menuNames.map(name => a(P.style.menuItem , href := P.menu.get(name).get , name ,key := name))
       ))
    })
    .build

  case class Props(menu : Map[String,String] ,style : Style)

  def apply(menu : Map[String,String] ,style : Style = new Style {}, ref: js.UndefOr[String] = "", key: js.Any = {}) = component.set(key, ref)(Props(menu,style))
}
