ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"


lazy val akkaVersion = "2.6.20"
val AkkaHttpVersion = "10.2.10"



lazy val root = (project in file("."))
  .settings(
    name := "akka-assign",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-remote" % akkaVersion,
      "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
//      "com.typesafe.play" %% "play-json" % "2.10.0-RC6",
      "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion

    )
  )
