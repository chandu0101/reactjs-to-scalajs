package chandu0101.scalajsreact.util

import scala.collection.mutable
import scala.scalajs.js

/**
 * Created by chandrasekharkode on 11/29/14.
 */
object Generator {

  val propTypesMap = Map(
    "React.PropTypes.array" -> "js.UndefOr[Array] = js.undefined",
    "React.PropTypes.bool" -> "js.UndefOr[Boolean] = js.undefined",
    "React.PropTypes.func" -> "js.UndefOr[js.Any] = js.undefined",
    "React.PropTypes.number" -> "js.UndefOr[Double] = js.undefined",
    "React.PropTypes.object" -> "js.UndefOr[Any] = js.undefined",
    "React.PropTypes.string" -> "js.UndefOr[String] = js.undefined",
    "React.PropTypes.node" -> "js.UndefOr[String] = js.undefined",
    "React.PropTypes.element" -> "js.UndefOr[ReactElement] = js.undefined",
    "React.PropTypes.array.isRequired" -> "js.UndefOr[Array]",
    "React.PropTypes.bool.isRequired" -> "js.UndefOr[Boolean]",
    "React.PropTypes.func.isRequired" -> "js.UndefOr[js.Any]",
    "React.PropTypes.number.isRequired" -> "js.UndefOr[Double]",
    "React.PropTypes.object.isRequired" -> "js.UndefOr[Any]",
    "React.PropTypes.string.isRequired" -> "js.UndefOr[String]",
    "React.PropTypes.node.isRequired" -> "js.UndefOr[String]",
    "React.PropTypes.element.isRequired" -> "js.UndefOr[ReactElement]",
    "React.PropTypes.any.isRequired" -> "js.UndefOr[js.Any]",
    "React.PropTypes.any" -> "js.UndefOr[js.Any]=js.undefined",
    "React.PropTypes.comp" -> "js.UndefOr[ReactElement]=js.undefined"
  )


  val propTypesToScala = Map(
    "React.PropTypes.bool" -> "Boolean = false",
    "React.PropTypes.func" -> "REventIAny = null ",
    "React.PropTypes.number" -> "Int = 0",
    "React.PropTypes.object" -> "Any = null",
    "React.PropTypes.string" -> "String = \"\" ",
    "React.PropTypes.node" -> " ReactElement = null",
    "React.PropTypes.element" -> "ReactElement = null",
    "React.PropTypes.comp" -> " TagMod = null",
    "React.PropTypes.any" -> "Any = null",
    "React.PropTypes.css" -> "CssClassType = Map()",
    "React.PropTypes.key" -> "js.Any = {}",
    "React.PropTypes.ref" -> " js.UndefOr[String] = \"\"",
    "React.PropTypes.array" -> "Vector]",
    "React.PropTypes.arrayOf(React.PropTypes.number)" -> "Vector[Int] = Vector()",
    "React.PropTypes.arrayOf(React.PropTypes.number).isRequired" -> "Vector[Int]",
    "React.PropTypes.array.isRequired" -> "Vector",
    "React.PropTypes.bool.isRequired" -> "Boolean",
    "React.PropTypes.func.isRequired" -> "REventIUnit",
    "React.PropTypes.number.isRequired" -> "Int ",
    "React.PropTypes.object.isRequired" -> "Any ",
    "React.PropTypes.string.isRequired" -> "String ",
    "React.PropTypes.node.isRequired" -> " ReactElement ",
    "React.PropTypes.element.isRequired" -> "ReactElement",
    "React.PropTypes.comp.isRequired" -> " TagMod",
    "React.PropTypes.any.isRequired" -> "Any"
  )

  def createGroupObject(gName: String, cName: String): String = {
    s"""
       |object $gName extends js.Object {
                       |
                       |def $cName : js.Dynamic = ???
                                    |
                                    |}
     """.stripMargin

  }

  def createComponentClass(gName: String, cName: String, rProps: String): String = {
    var classFields = ""
    var toJsProps = ""
    val jsComponentName = if (gName.isEmpty) cName else s"$gName.$cName"
    if (!rProps.isEmpty) {
      val props = rProps.split(",").map(str => {
        val result = str.split(":");
        (result(0).trim, result(1).trim)
      }).map {
        case (field, tpe) => (field, propTypesMap.getOrElse(tpe, "NotFoundScalaType"))
      }.toMap


      classFields = props.map { case (field, tpe) => s"$field : $tpe"}.mkString(",")

      toJsProps = props.keys.map(key => s"""$key.foreach(v => p.updateDynamic("$key")(v))""").mkString("\n")

    }
    s""" case class $cName($classFields) {
                                         |   def toJs: js.Object = {
                                         |     val p = js.Dynamic.literal()
                                         |    $toJsProps
        |     p
        |   }
        |
        |   def apply(children: TagMod*): ReactComponentU_ = {
        |     val f = $jsComponentName
        |     f(toJs, js.Array(children: _*)).asInstanceOf[ReactComponentU_]
        |   }
        | }""".stripMargin

  }


  def boilerPLate(rProps: String) = {
    val props = rProps.split(",").filter(s => !s.trim.isEmpty).map(str => {
      val result = str.split(":");
      (result(0).trim, result(1).trim)
    }).map {
      case (field, tpe) => (field, propTypesToScala.getOrElse(tpe, "NotFoundScalaType"))
    }.toMap

    val applyProperties = props.map { case (field, tpe) => s"$field : $tpe"}.mkString(",")

    val componenetProps = props.keys.mkString(",")

    val classProperties = props.map { case (field, tpe) => {
      val ntpe = if (tpe.indexOf("=") > 1) tpe.substring(0, tpe.indexOf("=")) else tpe; s"$field : $ntpe"
    }
    }.mkString(",")

    s"""
       |case class Props( $classProperties )
                                            |
                                            |def apply( $applyProperties ) =
                                            |component.set(key,ref)(Props($componenetProps))
    """.stripMargin
  }


  def generateCode(gName: String, cName: String, rProps: String) = {
    boilerPLate(rProps)
  }

  val CSS_SEPARATOR = '-'

  val JS_SEPERATOR = ":="


  def getJSKey(cssKey: String) = {
    if (cssKey.contains(CSS_SEPARATOR)) {
      var names =  cssKey.split(CSS_SEPARATOR).toList
//      var names = if (cssKey.head == CSS_SEPARATOR) cssKey.tail.split(CSS_SEPARATOR).toList else cssKey.split(CSS_SEPARATOR).toList
      names = names.head :: names.tail.map(_.capitalize)
      names.mkString
    } else cssKey
  }

  /**
   * this function generate jsStyles from css styles with ; separated
   * @param rawcss
   * @param autoPrefix
   * @return
   */
  def generateJsStyles(rawcss: String, autoPrefix: Boolean) = {
    val customStyles: mutable.Set[String] = mutable.Set()
    val jsStylesPair: mutable.Set[String] = mutable.Set()
    if (autoPrefix) {
      rawcss.trim.split(";").foreach(item => {
        val newRawCss = js.Dynamic.global.autoprefixer.process(item).css
        loop(newRawCss.toString, customStyles, jsStylesPair)
      })

    } else {
      loop(rawcss, customStyles, jsStylesPair)
    }
    s"""
       |========= jskey value ==========
       | ${jsStylesPair.toList.sorted.mkString(",\n")}
        |
        |========== custom reactStyles ========
        | ${customStyles.toList.sorted.mkString("\n")}
        |
     """.stripMargin
  }



  def loop(rawcss: String, customStyles: mutable.Set[String], jsStylesPair: mutable.Set[String]) = {
    rawcss.trim.split(";").foreach(item => {
      val cssKey = item.split(":")(0).trim
      val cssValue = item.split(":")(1).trim
      val jsKey = getJSKey(cssKey)
      customStyles += s"""val ${jsKey} = "${jsKey}".reactStyle """
      jsStylesPair += s"""${jsKey} := "${cssValue}" """
    })
  }


  def autoPrefixJsStyles(in: String) = {
    val cssStyles = in.trim.split(",").map(item => {
      val jsKey = item.split(JS_SEPERATOR)(0).trim
      val jsValue = item.split(JS_SEPERATOR)(1).trim.replaceAll("\"","")
      val cssKey = getCssKey(jsKey)
      s"$cssKey : $jsValue"
    })
    println(s"generated css${cssStyles.mkString(";")} ")
    generateJsStyles(cssStyles.mkString(";"),true)
  }

  def getCssKey(jsKey: String) = hyphenateStyleName(jsKey)

  val uppercasePattern = js.eval("/([A-Z])/g")

  val msPattern = js.eval("/^ms-/")

  def hyphenate(str : String) = str.asInstanceOf[js.Dynamic].replace(uppercasePattern,"-$1").toLowerCase().toString

  def hyphenateStyleName(str : String) = hyphenate(str).asInstanceOf[js.Dynamic].replace(msPattern, "-ms-").toString

}
