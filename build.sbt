import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys._

name := "gh-pages-generator"

version := "1.0"

scalaVersion := "2.11.2"


scalaJSSettings

persistLauncher := true

persistLauncher in Test := false

libraryDependencies += "org.scala-lang.modules.scalajs" %%% "scalajs-jquery" % "0.6"

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