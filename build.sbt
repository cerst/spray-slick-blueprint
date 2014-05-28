name := "spray-slick-blueprint"

scalaVersion := "2.10.4"

seq(Revolver.settings: _*)

resolvers += "spray repo" at "http://repo.spray.io"

libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.3.0",
    "com.typesafe.akka" %% "akka-slf4j" % "2.3.0",
    "ch.qos.logback" % "logback-classic" % "1.1.2",
    // redirect c3p0 logging to slf4j
    "org.slf4j" % "log4j-over-slf4j" % "1.7.7",
    "io.spray" % "spray-can" % "1.3.1",
    "io.spray" % "spray-routing" % "1.3.1",
    "io.spray" %%  "spray-json" % "1.2.6",
    "com.typesafe.slick" %% "slick" % "2.0.2",
    "postgresql" % "postgresql" % "9.1-901.jdbc4",
    "c3p0" % "c3p0" % "0.9.1.2",
    // testing
    "org.scalatest" %% "scalatest" % "2.1.6" % "test",
    "org.mockito" % "mockito-core" % "1.9.5",
    "com.typesafe.akka" %% "akka-testkit" % "2.3.0" % "test",
    "io.spray" % "spray-testkit" % "1.3.1" % "test"
)
