resolvers += "jgit-repo" at "http://download.eclipse.org/jgit/maven"

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.2.2")

addSbtPlugin("com.github.gseitz" % "sbt-release" % "0.6")

addSbtPlugin("org.ensime" % "ensime-sbt-cmd" % "0.1.1")  

// run git command from the sbt console
addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.5.0")


