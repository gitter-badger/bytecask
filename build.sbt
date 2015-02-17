organization := "net.crispywalrus.bytecask"

name := "bytecask"

licenses += ("GPL-3.0", url("http://www.gnu.org/copyleft/gpl.html"))

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.7.10",
  "org.xerial.snappy" % "snappy-java" % "1.1.1.6",
  "org.slf4j" % "slf4j-simple" % "1.7.10" % Test,
  "org.scalatest" %% "scalatest"  % "2.2.4"  % Test
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

// buildinfo includes just that, build info, in a generated class in the delivered artifact
buildInfoSettings

sourceGenerators in Compile <+= buildInfo

buildInfoKeys := Seq[BuildInfoKey](
  name,
  version,
  scalaVersion,
  libraryDependencies in Compile
)

buildInfoPackage := "net.crispywalrus.bytecask"

seq(bintraySettings:_*)
