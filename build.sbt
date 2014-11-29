import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys._

name := "reactjs-scalajs"

version := "1.0"

scalaVersion := "2.11.4"


scalaJSSettings

persistLauncher := true

persistLauncher in Test := false


libraryDependencies += "com.github.japgolly.scalajs-react" %%% "core" % "0.6.0"

// React itself
//   (react-with-addons.js can be react.js, react.min.js, react-with-addons.min.js)
jsDependencies += "org.webjars" % "react" % "0.12.1" / "react-with-addons.js" commonJSName "React"


//DOM, which doesn't exist by default in the Rhino runner. To make the DOM available in Rhino
jsDependencies += scala.scalajs.sbtplugin.RuntimeDOM

// creates single js resource file for easy integration in html page
skip in packageJSDependencies := false

// uTest settings
utest.jsrunner.Plugin.utestJsSettings


// copy  javascript files to js folder,that are generated using fastOptJS/fullOptJS

crossTarget in (Compile, fullOptJS) := file("js")

crossTarget in (Compile, fastOptJS) := file("js")

crossTarget in (Compile, packageJSDependencies) := file("js")

crossTarget in (Compile, packageScalaJSLauncher) := file("js")

artifactPath in (Compile, fastOptJS) := ((crossTarget in (Compile, fastOptJS)).value /
  ((moduleName in fastOptJS).value + "-opt.js"))