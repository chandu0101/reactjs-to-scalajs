package chandu0101.scalajsreact.util

import org.scalajs.dom

/**
 * Created by chandrasekharkode on 12/29/14.
 */
object Navigate {

  def to(value : String) = dom.window.location.hash = value

}
