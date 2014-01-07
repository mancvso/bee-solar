import AssemblyKeys._

resolvers += "spray repo" at "http://repo.spray.io"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "sprest snapshots" at "http://markschaake.github.com/releases"

scalaVersion := "2.10.3"

name := "bee-solar"

scalacOptions += "-feature"

scalacOptions += "-deprecation"

libraryDependencies ++= Seq(
	"com.typesafe.akka"             %% "akka-actor"           % "2.2.1",
	"ch.qos.logback"                % "logback-classic"       % "1.0.1",
  "joda-time"                     % "joda-time"             % "2.1",
  "org.joda"                      % "joda-convert"          % "1.3",
  "sprest"                        %% "sprest-reactivemongo" % "0.3.0",
	"org.specs2"                    %% "specs2"               % "2.2" % "test",
  "com.github.jodersky"           %% "flow"                 % "1.0.1-SNAPSHOT"
)

seq(Revolver.settings: _*)

seq(coffeeSettings: _*)

//seq(lessSettings:_*)

assemblySettings
