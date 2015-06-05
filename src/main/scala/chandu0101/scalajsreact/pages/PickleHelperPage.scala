package chandu0101.scalajsreact.pages

import chandu0101.scalajsreact.components.InOutGenerator
import japgolly.scalajs.react.vdom.all._
import japgolly.scalajs.react.{BackendScope, ReactComponentB, ReactEventI}

import scala.collection.mutable

/**
 * Created by chandrasekharkode on 2/27/15.
 */
object PickleHelperPage {

  val scalaTypes = Stream("String", "Int", "Boolean", "Array", "List", "js.Array", "Set", "Double", "Long")

  class Backend(t: BackendScope[_, _]) {

    val variablesAndTypes = mutable.Map[String, String]()

    var className: String = ""

    def isKnownScalaType(in: String) = scalaTypes.exists(s => in.startsWith(s))

    def getToJson = {
      val json = variablesAndTypes.map {
        case (v, t) => {
          val jv = if (v == "tpe") "type" else v
          if (isKnownScalaType(t)) s""" p.updateDynamic("$jv")($v) """
          else if (t.contains("UndefOr[")) s""" ${v}.foreach(v => p.updateDynamic("$jv")(v)) """
          else s""" "$v" -> $v.toJson """
        }
      }.mkString("\n")
      s"""def toJson = {
         |val p = json()
         | $json
          | p
          |}
          |""".stripMargin
    }

    def getFromJson = {

      val fromjson = variablesAndTypes.map {

        case (key, value) => {
          val jkey = if (key == "tpe") "`type`" else key
          if (value == "String") s"""$key = obj.$jkey.toString"""
          else if (isKnownScalaType(value)) s"""$key = obj.$key.asInstanceOf[$value]"""
          else if (value.contains("UndefOr[")) s""" $key = if(js.isUndefined(obj.$key)) js.undefined else obj.$key.asInstanceOf[${value.replace("js.UndefOr[", "").replace("UndefOr[", "").replace("]", "")}]"""
          else s"""$key = ${value}.fromJson(obj.$key)"""
        }
      }.mkString(",")

      s"""object $className {
                             |def fromJson(obj : js.Dynamic) = $className($fromjson)
                                                                                     |}
       """.stripMargin
    }

    def generatePickle(in: String) = {
      in.trim.split(",").foreach(s => {
        val variable = s.split(":").head.trim
        var tpe = s.split(":").last.trim
        if (tpe.contains("=")) {
          tpe = tpe.split("=").head.trim
        }
        variablesAndTypes += variable -> tpe
      })
      s"""
         |======== ToJson =====
         | $getToJson
          |====== From Json ====
          | $getFromJson
          | 
      """.stripMargin
    }

    def onClassNameChange(e: ReactEventI) = {
      className = e.target.value

    }

  }

  val component = ReactComponentB[Unit]("FacadeHelper")
    .stateless
    .backend(new Backend(_))
    .render((P, S, B) => {
    div(padding := "30px")(
      h3("Case class to json"),
      input(tpe := "text", onChange ==> B.onClassNameChange, placeholder := "enter classname here"),
      InOutGenerator(onButtonClick = B.generatePickle)
    )
  })
    .buildU


  def apply() = component()

}
