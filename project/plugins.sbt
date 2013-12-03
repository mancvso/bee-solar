resolvers += "spray repo" at "http://repo.spray.io"

addSbtPlugin("io.spray" % "sbt-revolver" % "0.7.1")

addSbtPlugin("me.lessis" % "coffeescripted-sbt" % "0.2.3")

resolvers += "softprops-maven" at "http://dl.bintray.com/content/softprops/maven"

libraryDependencies += "me.lessis" %% "lesst" % "0.1.1"

addSbtPlugin("me.lessis" % "less-sbt" % "0.2.1")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.9.2")
