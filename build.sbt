name := "kafka-streams-from-scratch"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "1.0.1"
libraryDependencies += "org.apache.kafka" % "kafka-streams" % "1.0.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test
libraryDependencies += "net.manub" %% "scalatest-embedded-kafka" % "1.1.0" % Test