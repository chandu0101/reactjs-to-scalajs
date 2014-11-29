import org.scalajs.jquery.jQuery
import utest._
import utest.framework.TestSuite

/**
 * Created by chandrasekharkode on 11/14/14.
 */
object AppTest extends TestSuite {

  //Initialize App
  App.setupUI()

  def tests = TestSuite {
    'ScalaJsRocks {
      assert(jQuery("footer:contains('Happy Coding!')").length == 1)
    }
  }

}
