import sbt._
import Keys._
import org.ensime.sbt.util.SExp._
import org.ensime.sbt.Plugin.Settings.ensimeConfig

organization := "com.github.bytecask"

name := "bytecask"

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.1"

resolvers ++= Seq(
  "maven" at "http://repo1.maven.org/maven2"
)

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "org.slf4j" % "slf4j-simple" % "1.7.5" % "runtime",
  "org.xerial.snappy" % "snappy-java" % "1.1.0-M3",
  "org.scalatest" %% "scalatest"  % "1.9.1"  % "test"
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

fork in run := true

javaOptions in run += "-Droot-level=OFF -server -XX:+TieredCompilation -XX:+AggressiveOpts"

// publishTo <<= (version) { version: String =>
//   val nexus = "https://oss.sonatype.org/content/repositories/"
//   if (version.trim.endsWith("SNAPSHOT"))
//     Some("snapshots" at nexus + "snapshots/")
//   else
//     Some("releases"  at nexus + "releases/")
// }

// credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

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

ensimeConfig := sexp(
  key(":formatting-prefs"), sexp(
    key(":alignParameters"), true,
    key(":alignSingleLineCaseStatements"), true,
    key(":rewriteArrowSymbols"), true,
    key(":compactStringConcatenation"), true,
    key(":indentWithTabs"), false
  )
)
