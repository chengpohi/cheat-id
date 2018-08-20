import Settings._

scalaVersion := "2.12.6"

lazy val root = project
  .in(file("."))
  .settings(commonSettings: _*)
  .settings(rootProjectSettings: _*)
