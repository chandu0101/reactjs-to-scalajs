package chandu0101.scalajsreact.routing

import chandu0101.scalajsreact.components.ReactNavMenu
import chandu0101.scalajsreact.pages.{CssToJSGenerator, Homepage, JsStylesPrefixer}
import chandu0101.scalajsreact.util.Navigate
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router._
import japgolly.scalajs.react.vdom.all._

/**
 * Created by chandrasekharkode on 12/9/14.
 */
object AppRouter {

  object AppPage extends RoutingRules {

    val root = register(rootLocation(Homepage()))

    val cssToJs = register(location("#csstojs", CssToJSGenerator()))

    val jsPrefixer = register(location("#jsprefixer", JsStylesPrefixer()))

    register(removeTrailingSlashes)

    override protected val notFound = redirect(root, Redirect.Replace)

    override protected def interceptRender(i: InterceptionR): ReactElement =
      div(
        ReactNavMenu(menu = Map("Home" -> "#",
          "CssToJs" -> cssToJs.path.value,
          "JsPrefixer" -> jsPrefixer.path.value)),
        i.element,
        div(
          hr(),
          p(textAlign := "center")("Built using awesome ScalajS")
        )
      )


    def getTitle(loc: String) = {
      if (loc.contains("materialui")) "Material-UI"
      else if (loc.contains("reacttable")) "React Table"
      else ""
    }

    def onMenuIconButtonTouchTap(e: ReactEventI) = Navigate.to("#")
  }


//  val baseUrl = BaseUrl.fromWindowOrigin / "reactjs-scalajs/"
  val baseUrl = BaseUrl.fromWindowOrigin / "sjru/"
  
  val C = AppPage.router(baseUrl)
}
