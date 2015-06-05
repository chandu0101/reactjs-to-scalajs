package chandu0101.scalajsreact.pages

import chandu0101.scalajsreact.components.InOutGenerator
import japgolly.scalajs.react.vdom.all._
import japgolly.scalajs.react.{BackendScope, ReactComponentB}

import scala.collection.mutable

/**
 * Created by chandrasekharkode .
 */
object FacadeHelperPage {


  val fieldDefaultValuesMap = Map("Boolean2" -> "false",
    "js.UndefOr[Boolean2]" -> "false",
    "UndefOr[Boolean2]" -> "false"
  )

  class Backend(t: BackendScope[_, _]) {


    def generateCompanionObject(in: String) = {
      val fieldDefs = mutable.Set[String]()
      val jsonValue = mutable.Set[String]()
      val traitType = in.split("\n").filter(s => s.contains("trait")).head.replaceAll("{", "")
      val traitName2 = traitType.replaceAll("trait", "").replaceAll(" ", "")

      val traitName = traitName2.substring(0, traitName2.indexOf("extends"))

      in.replaceAll("var", "")
        .replaceAll("val", "")
        .split("\n")
        .filter(s => s.nonEmpty && s.contains("js.native"))
        .foreach(s => {
        val name = s.trim.split(":")(0).trim
        val tpe = s.trim.split(":")(1).split("=")(0).replace("js.UndefOr[", "").replace("UndefOr[", "").replaceAll("]", "").trim
        fieldDefs += s""" def ${name}(v: ${tpe}) = jsOpt("${name}",v)"""
      })
      s"""
         |$traitType
          |
          |object $traitName extends ${traitName}Builder(noOpts)
                                                   |
                                                   |class ${traitName}Builder(val dict :OptMap) extends JSOptionBuilder[${traitName},${traitName}Builder](new ${traitName}Builder(_)) {
                                                                                                                                                                            |
                                                                                                                                                                            | ${fieldDefs.mkString("\n\n")}

          |}
          |
        """.stripMargin
    }

  }

  val defaultInput = """var include_docs : Boolean = js.native
                       |  var conflicts : Boolean = js.native
                       |  var attachments : Boolean = js.native
                       |  var startkey : String = js.native
                       |  var endkey : String = js.native
                       |  var inclusive_end : String = js.native
                       |  var limit : Int = js.native
                       |  var skip : Int = js.native
                       |  var descending : Boolean = js.native
                       |  var key : String = js.native
                       |  var keys : js.Array[String] = js.native""".stripMargin

  val component = ReactComponentB[Unit]("FacadeHelper")
    .stateless
    .backend(new Backend(_))
    .render((P, S, B) => {
    div(padding := "30px")(
      h3("Facade options trait Companion object Generator"),
      InOutGenerator(onButtonClick = B.generateCompanionObject, inputValue = defaultInput)
    )
  })
    .buildU


  def apply() = component()
}
