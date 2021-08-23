// shadow sbt-scalajs' crossProject and CrossType from Scala.js 0.6.x
import sbt.Keys.libraryDependencies
import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}

val sharedSettings = Seq(
  name := "kandinsky",
  description := "Libary for creating vector graphics by composing simple functions into successfully more complex",
  version := "0.1.1-SNAPSHOT",
  organization := "skac112",
  scalaVersion := "2.12.8",
  libraryDependencies += "skac112" %%% "miro" % "1.1.2-SNAPSHOT",
  libraryDependencies += "skac112" %%% "vgutils" % "0.1.4-SNAPSHOT",
  libraryDependencies += "com.lihaoyi" %%% "utest" % "0.6.3" % "test",
)

val jvmSettings = Seq(
  libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1",
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)

val jsSettings = Seq()

lazy val kandinsky = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Full)
  .in(file("."))
  .settings(sharedSettings)
  .jsSettings(jsSettings) // defined in sbt-scalajs-crossproject
  .jvmSettings(jvmSettings)

lazy val kandinskyJVM = kandinsky.jvm
lazy val kandinskyJS = kandinsky.js