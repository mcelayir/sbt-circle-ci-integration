name := "circleci-sbt-integration"

version := "0.1"

scalaVersion := "2.12.8"

val root = project
  .in(file("."))
  .enablePlugins()
  .settings(
    libraryDependencies ++= Seq(
      dependencies.scalaCheck,
      dependencies.scalaTest,
    ))

lazy val dependencies = new {

  val version = new {
    val scalaTest = "3.0.1"
    val scalaCheck = "1.13.5"
  }

  val scalaCheck = "org.scalacheck" %% "scalacheck" % version.scalaCheck % Test
  val scalaTest = "org.scalatest" %% "scalatest" % version.scalaTest % Test
}
