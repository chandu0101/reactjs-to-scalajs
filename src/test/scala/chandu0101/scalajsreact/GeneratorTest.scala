package chandu0101.scalajsreact

import chandu0101.scalajsreact.util.Generator
import Generator
import utest._
import utest.framework.TestSuite

/**
 * Created by chandrasekharkode on 11/29/14.
 */
object GeneratorTest extends TestSuite {


   def tests = TestSuite {

    'ShouldGenerateObject {
      val expected = """
                       |object ReactBootstrap extends js.Object {
                       |
                       |def Panel : js.Dynamic = ???
                       |
                       |}
                     """.stripMargin
      assert(Generator.createGroupObject("ReactBootstrap", "Panel") == expected)
    }

  }
}
