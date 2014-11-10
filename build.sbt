import sbt._
import Keys._

organization := "com.github.bytecask"

name := "bytecask"

scalaVersion := "2.11.4"

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.7.7",
  "org.slf4j" % "slf4j-simple" % "1.7.7", 
  "org.xerial.snappy" % "snappy-java" % "1.1.1.6",
  "org.scalatest" %% "scalatest"  % "2.2.2"  % "test"
)

scalacOptions ++= Seq(
  "-feature",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-language:reflectiveCalls",
  "-Yinline-warnings",
  "-deprecation",
  "-optimise",
  "-encoding", "utf8"
)

fork in run := true

javaOptions in run += "-Droot-level=OFF -XX:+TieredCompilation -XX:+AggressiveOpts -server -Xmx512M -Xss2M"

javacOptions ++= Seq("-source", "1.7")

publishTo <<= (version) { version: String =>
//  val nexus = "https://oss.sonatype.org/content/repositories/"
  val nexus = "http://f1tst-linbld100/nexus/content/repositories/"
  if (version.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "snapshots/")
  else
    Some("releases"  at nexus + "releases/")
}

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

// buildinfo includes just that, build info, in a generated class in the delivered artifact
buildInfoSettings

sourceGenerators in Compile <+= buildInfo

buildInfoKeys := Seq[BuildInfoKey](
  name,
  version,
  scalaVersion,
  libraryDependencies in Compile
)

buildInfoPackage := "com.github.bytecask"

// release configuration
releaseSettings
