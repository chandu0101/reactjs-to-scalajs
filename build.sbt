
name := "reactjs-scalajs"

version := "1.0"

scalaVersion := "2.11.5"

lazy val root = project.in(file(".")).
  enablePlugins(ScalaJSPlugin)

val scalajsReactVersion = "0.8.0"

persistLauncher := true

persistLauncher in Test := false


libraryDependencies += "com.chandu0101.scalajs-react-components" %%% "core" % "0.1.1-SNAPSHOT"

libraryDependencies += "com.github.japgolly.scalajs-react" %%% "extra" % scalajsReactVersion

resolvers += "bintray/non" at "http://dl.bintray.com/non/maven"


//libraryDependencies += "com.github.japgolly.fork.scalaz" %%% "scalaz-effect" % "7.1.0-4"

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

//crossTarget in (Compile, packageScalaJSLauncher) := file("js")

artifactPath in (Compile, fastOptJS) := ((crossTarget in (Compile, fastOptJS)).value /
  ((moduleName in fastOptJS).value + "-opt.js"))

scalacOptions += "-feature"

//sources in (Compile,doc) := Seq.empty

//target in Compile in doc := baseDirectory.value / "api"

