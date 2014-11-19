name := """type-level-programming-for-beginners-slides"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "com.chuusai" %% "shapeless" % "2.0.0"
)

TwirlKeys.templateImports += "views._"
