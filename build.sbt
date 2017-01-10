name := "AkkaHttpTest"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.0.1",
  "io.spray" %% "spray-routing-shapeless2" % "1.3.3",
  "io.spray" %% "spray-can" % "1.3.3"
)

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

javacOptions in doc := Seq("-source", "1.7")
