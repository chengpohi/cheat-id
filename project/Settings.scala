import sbt.Keys._
import sbt._
import sbtassembly.AssemblyKeys._

object Settings {
  lazy val commonSettings = Seq(
    version := "0.1-SNAPSHOT",
    organization := "com.github.chengpohi",
    scalaVersion := "2.12.6",
    scalacOptions ++= Seq("-Ywarn-unused",
      "-Ywarn-unused-import",
      "-feature",
      "-language:implicitConversions",
      "-language:postfixOps"),
    resolvers += Resolver.mavenLocal,
    resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases",
    test in assembly := {}
  )

  lazy val rootProjectSettings = Seq(
    name := "id-generator",
    scalacOptions ++= Seq(
      "-Ywarn-unused",
      "-Ywarn-unused-import",
      "-feature",
      "-language:implicitConversions",
      "-language:postfixOps"
    ),
    libraryDependencies ++= dependencies
  )

  val dependencies = Seq(
    "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  )
}
