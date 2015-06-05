package chandu0101.scalajsreact.routing

import chandu0101.scalajsreact.components.{Footer, Menu, TopNav}
import chandu0101.scalajsreact.pages.{FacadeHelperPage, PickleHelperPage, ReactJSWrapperPage}
import japgolly.scalajs.react.extra.router2.{Resolution, RouterConfigDsl, RouterCtl, _}
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom

object AppRouter {


  sealed trait AppPage

  case object ReactJSWrapper extends AppPage

  case object PickleHelper extends AppPage

  case object FacadeHelper extends AppPage


  val config = RouterConfigDsl[AppPage].buildConfig { dsl =>
    import dsl._
    (trimSlashes
      | staticRoute(root, ReactJSWrapper) ~> render(ReactJSWrapperPage())
      | staticRoute("#picklehelper", PickleHelper) ~> render(PickleHelperPage())
      | staticRoute("#facadehelper", FacadeHelper) ~> render(FacadeHelperPage())
      ).notFound(redirectToPage(ReactJSWrapper)(Redirect.Replace))
      .renderWith(layout)
  }

  val mainMenu = Vector(Menu("ReactJSWrapper", ReactJSWrapper),
    Menu("PickleHelper", PickleHelper),
    Menu("FacadeHelper", FacadeHelper)
  )

  def layout(c: RouterCtl[AppPage], r: Resolution[AppPage]) = {
    <.div(
      TopNav(TopNav.Props(mainMenu, r.page, c)),
      r.render(),
      Footer()
    )
  }

  val baseUrl = BaseUrl.fromWindowOrigin / "reactjs-scalajs/"


  val router = Router(baseUrl, config)

}
