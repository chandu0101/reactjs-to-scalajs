package chandu0101.scalajsreact.util

import scala.scalajs.js


object Generator {


  val reactJSPropsToWrapperPropsMap = Map(
    "PropTypes.bool" -> "UndefOr[Boolean]=undefined",
    "PropTypes.func" -> "UndefOr[js.Function] = undefined ",
    "PropTypes.Date" -> "UndefOr[js.Date] = undefined ",
    "PropTypes.number" -> "UndefOr[Int] = undefined",
    "PropTypes.arrayOf(PropTypes.object)" -> "UndefOr[js.Array[js.Object]] = undefined",
    "PropTypes.object" -> "UndefOr[js.Object] = undefined",
    "PropTypes.string" -> "UndefOr[String] = undefined",
    "PropTypes.node" -> " UndefOr[ReactElement] = undefined",
    "PropTypes.arrayOf(PropTypes.number)" -> " UndefOr[js.Array[Int]] = undefined",
    "PropTypes.element" -> "UndefOr[ReactElement] = undefined",
    "PropTypes.comp" -> "UndefOr[ReactNode] = undefined",
    "PropTypes.ImageSource" -> "UndefOr[ImageSource] = undefined",
    "PropTypes.object" -> "UndefOr[js.Object] = undefined",
    "PropTypes.array" -> "UndefOr[js.Array] = undefined",
    "PropTypes.array.isRequired" -> "js.Array",
    "PropTypes.bool.isRequired" -> "Boolean",
    "PropTypes.Date.isRequired" -> "js.Date",
    "PropTypes.arrayOf(PropTypes.number).isRequired" -> "js.Array[Int]",
    "PropTypes.func.isRequired" -> "js.Function",
    "PropTypes.number.isRequired" -> "Int",
    "PropTypes.object.isRequired" -> "js.Object",
    "PropTypes.string.isRequired" -> "String ",
    "PropTypes.node.isRequired" -> " ReactElement ",
    "PropTypes.element.isRequired" -> "ReactElement",
    "PropTypes.comp.isRequired" -> " TagMod",
    "PropTypes.object.isRequired" -> "js.Object"
  )


  val CSS_SEPARATOR = '-'

  val JS_SEPERATOR = ":="


  val uppercasePattern = js.eval("/([A-Z])/g")

  val msPattern = js.eval("/^ms-/")

  def hyphenate(str: String) = str.asInstanceOf[js.Dynamic].replace(uppercasePattern, "-$1").toLowerCase().toString

  def hyphenateStyleName(str: String) = hyphenate(str).asInstanceOf[js.Dynamic].replace(msPattern, "-ms-").toString

  def generateWrapperClass(in: String, componentName: String,jsName : String, withChildren : Boolean) = {
    val props = in.replaceAll("React.PropTypes", "PropTypes").split("\n")
      .filter(s => s.contains("PropTypes."))
      .map(s => {
      val name = s.split(":").head.trim
      val tpe = s.split(":").last.trim.replace(",", "")
      (name -> tpe)
    }).toMap


    def getGenericType(tpe: String) = {
      if (tpe.contains(".isRequired")) tpe.replace(".isRequired","").replace("PropTypes.","").trim
      else s"UndefOr[${tpe.replace("PropTypes.","")}] = undefined"
    }

    val wrapperProps = props.map {
      case (name, tpe) => {
        s"$name : ${reactJSPropsToWrapperPropsMap.getOrElse(tpe, getGenericType(tpe))}"
      }
    }.mkString(",\n")


    val toJS = props.map { case (name, tpe) => {
      if (!tpe.contains("isRequired"))
        s"""$name.foreach(v => p.updateDynamic("$name")(v))"""
      else s"""p.updateDynamic("$name")($name)"""
    }
    }.mkString("\n")
   if(withChildren)
    s"""
       |case class $componentName($wrapperProps) {
       |def toJS = {
       |  val p = js.Dynamic.literal()
       |  $toJS
       |  p
       |}
       |
       |def apply(children : ReactNode*) = {
       | val f = $jsName
       | f(toJS,children.toJsArray).asInstanceOf[ReactComponentU_]
       |}
       |}
     """.stripMargin
    else
     s"""
        |object $componentName {
        |
        |def apply($wrapperProps) = {
        |
        | val toJS = js.Dynamic.literal()
        | $toJS
        | f(toJS).asInstanceOf[ReactComponentU_]
        |}
        |
        |}
      """.stripMargin
  }
}
