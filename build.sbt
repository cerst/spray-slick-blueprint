name := "Skeleton-App"

scalaVersion := "2.10.3"

seq(Revolver.settings: _*)

resolvers += "spray repo" at "http://repo.spray.io"

libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.2.3",
    "com.typesafe.akka" %% "akka-slf4j" % "2.2.3",
    "ch.qos.logback" % "logback-classic" % "1.0.13",
    // redirect c3p0 logging to slf4j
    "org.slf4j" % "log4j-over-slf4j" % "1.7.5",
    "io.spray" % "spray-can" % "1.2-RC3",
    "io.spray" % "spray-routing" % "1.2-RC3",
    "io.spray" %%  "spray-json" % "1.2.5",
    "com.typesafe.slick" %% "slick" % "1.0.1",
    "postgresql" % "postgresql" % "9.1-901.jdbc4",
    "c3p0" % "c3p0" % "0.9.1.2",
    // testing
    "org.scalatest" %% "scalatest" % "2.0" % "test",
    "org.mockito" % "mockito-core" % "1.9.5",
    "com.typesafe.akka" %% "akka-testkit" % "2.2.3" % "test",
    "io.spray" % "spray-testkit" % "1.2-RC3" % "test"
)
