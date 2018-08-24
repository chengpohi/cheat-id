import Settings._

scalaVersion := "2.12.6"

resolvers += Resolver.jcenterRepo
publishTo := Some(
  "Bintray API Realm" at "https://api.bintray.com/maven/chengpohi/cheat-id/cheat-id/;publish=1")
credentials += Credentials(new File("credentials.properties"))

lazy val root = project
  .in(file("."))
  .settings(commonSettings: _*)
  .settings(rootProjectSettings: _*)
