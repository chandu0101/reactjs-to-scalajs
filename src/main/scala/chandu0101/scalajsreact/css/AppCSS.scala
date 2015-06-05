package chandu0101.scalajsreact.css

import chandu0101.scalajsreact.components.TopNav
import chandu0101.scalajsreact.pages.ReactJSWrapperPage

import scalacss.ScalaCssReact._
import scalacss.mutable.GlobalRegistry
import scalacss.Defaults._

object AppCSS {

  def load = {
    GlobalRegistry.register(
      GlobalStyle,
    ReactJSWrapperPage.Style,
      TopNav.Style)
    GlobalRegistry.onRegistration(_.addToDocument())
  }
}

