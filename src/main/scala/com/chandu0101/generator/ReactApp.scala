package com.chandu0101.generator

import com.chandu0101.generator.css.BootstrapCSS._
import japgolly.scalajs.react.vdom.ReactVDom.ReactVExt_Attr
import japgolly.scalajs.react.vdom.ReactVDom.all._
import japgolly.scalajs.react.{ReactEventI, React, BackendScope, ReactComponentB}
import org.scalajs.dom

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

/**
 * Created by chandrasekharkode on 11/14/14.
 */


object ReactApp extends JSApp {
  @JSExport
  override def main(): Unit =  React.render(generator(), dom.document.getElementById("container"))


  case class State(gName: String, cName: String, rProps: String, sCode: String)

  class Backend(t: BackendScope[_, State]) {

    def generate() = ???

    def ongNameChange(e: ReactEventI) = t.modState(_.copy(gName = e.target.value))

    def oncNameChange(e: ReactEventI) = t.modState(_.copy(cName = e.target.value))

    def onPropsChange(e: ReactEventI) = t.modState(_.copy(rProps = e.target.value))
  }

  val generator = ReactComponentB[Unit]("generator")
    .initialState(State("", "", "", ""))
    .backend(new Backend(_))
    .render((_, S, B) => {
    div(cls := container)(
      div(cls := row)(
        form(role := "form")(

          div(cls := s"$col_md_3 $form_group")(
           input(cls := s"$form_control" ,marginBottom := "20px")(placeholder := "Component Group Name" , value := S.gName , onchange ==> B.ongNameChange),
           input(cls := form_control)(placeholder := "Component name" , value := S.cName ,onchange ==> B.oncNameChange)
          ),
          div(cls := s"$col_md_9 $form_group")(
           textarea(cls := form_control)(height := "150px", value := S.rProps , placeholder := "place props here" ,onchange ==> B.onPropsChange)
          ),
          button(cls := s"$btn $btn_warning btn-lg $center_block" , marginTop := "20px" )(onclick -->  B.generate)("Generate")
        )
      ),

    div(cls := row , marginTop := "20px") (
      textarea(cls := form_control)(height := "190px", value := S.sCode , placeholder := "generated code" )
    )

    )
  })
    .buildU

}
