import sbt._
import Keys._

object MinimalBuild extends Build {
  

  lazy val buildVersion =  "2.3.0"
  lazy val playVersion =  "2.3.0"
  
  lazy val typesafeSnapshot = "Typesafe Snapshots Repository" at "http://repo.typesafe.com/typesafe/snapshots/"
  lazy val typesafe = "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
  lazy val repo = if (buildVersion.endsWith("SNAPSHOT")) typesafeSnapshot else typesafe  
  
  lazy val root = Project(id = "play-plugins-mailer", base = file("."), settings = Project.defaultSettings).settings(
    version := buildVersion,
    scalaVersion := "2.11.1",
    publishTo <<= (version) { version: String =>
                val nexus = "https://private-repo.typesafe.com/typesafe/"
                if (version.trim.endsWith("SNAPSHOT")) Some("snapshots" at nexus + "maven-snapshots/")
                else                                   Some("releases"  at nexus + "maven-releases/")
    },
    organization := "com.typesafe",
    resolvers += repo,
    javacOptions ++= Seq("-source","1.6","-target","1.6", "-encoding", "UTF-8"),
    javacOptions += "-Xlint:unchecked",
    libraryDependencies += "com.typesafe.play" %% "play" % playVersion % "provided",
    libraryDependencies += "org.apache.commons" % "commons-email" % "1.3.2",
    libraryDependencies += "com.typesafe" %% "play-plugins-util" % buildVersion
  )
}
