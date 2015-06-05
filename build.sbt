
name := "reactjs-scalajs"

version := "1.0"

scalaVersion := "2.11.6"

lazy val root = project.in(file(".")).
  enablePlugins(ScalaJSPlugin)

val scalajsReactVersion = "0.9.0"
val scalaCssVersion = "0.2.0"

persistLauncher := true

persistLauncher in Test := false


libraryDependencies ++= Seq(
//  "com.chandu0101.scalajs-react-components" %%% "core" % "0.1.1-SNAPSHOT",
  "com.github.japgolly.scalacss" %%% "core" % scalaCssVersion,
  "com.github.japgolly.scalacss" %%% "ext-react" % scalaCssVersion,
  "com.github.japgolly.scalajs-react" %%% "extra" % scalajsReactVersion)


// React itself
//   (react-with-addons.js can be react.js, react.min.js, react-with-addons.min.js)
//DOM, which doesn't exist by default in the Rhino runner. To make the DOM available in Rhino
jsDependencies ++=  Seq(
  "org.webjars" % "react" % "0.12.1" / "react-with-addons.js" commonJSName "React")

jsDependencies += ProvidedJS / "autoprefixer.js"

// creates single js resource file for easy integration in html page
skip in packageJSDependencies := false

// uTest settings
//utest.jsrunner.Plugin.utestJsSettings


// copy  javascript files to js folder,that are generated using fastOptJS/fullOptJS

crossTarget in (Compile, fullOptJS) := file("js")

crossTarget in (Compile, fastOptJS) := file("js")

crossTarget in (Compile, packageJSDependencies) := file("js")

crossTarget in (Compile, doc) := file("docs")

crossTarget in (Compile, packageScalaJSLauncher) := file("js")

artifactPath in (Compile, fastOptJS) := ((crossTarget in (Compile, fastOptJS)).value /
  ((moduleName in fastOptJS).value + "-opt.js"))

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature",
  "-language:postfixOps", "-language:implicitConversions",
  "-language:higherKinds", "-language:existentials")

