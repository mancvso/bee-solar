import AssemblyKeys._

organization  := "com.example"

version       := "0.1"

scalaVersion  := "2.10.3"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "spray repo"   at "http://repo.spray.io/",
  "Mark Schaake" at "http://markschaake.github.com/snapshots"
)

libraryDependencies ++= {
  val akkaV = "2.2.3"
  val sprayV = "1.2.0"
  Seq(
    "io.spray"            %   "spray-can"           % sprayV,
    "io.spray"            %   "spray-routing"       % sprayV,
    "io.spray"            %   "spray-testkit"       % sprayV,
    "com.typesafe.akka"   %%  "akka-actor"          % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"        % akkaV,
    "org.specs2"          %%  "specs2"              % "2.2.3" % "test",
    "com.github.jodersky" %% "flow"                 % "1.0.1-SNAPSHOT",
    "sprest"              %% "sprest-reactivemongo" % "0.3.0"
  )
}

seq(Revolver.settings: _*)

seq(coffeeSettings: _*)

assemblySettings