import sbt.Keys._
import sbt._

object Settings {
  lazy val commonSettings = Seq(
    version := "0.1",
    organization := "com.github.chengpohi",
    scalaVersion := "2.12.6",
    scalacOptions ++= Seq("-Ywarn-unused",
      "-Ywarn-unused-import",
      "-feature",
      "-language:implicitConversions",
      "-language:postfixOps"),
    resolvers += Resolver.mavenLocal
  )

  lazy val rootProjectSettings = Seq(
    name := "cheat-id",
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
