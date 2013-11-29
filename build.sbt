organization  := "com.datactil"

version       := "0.0.2"

scalaVersion  := "2.10.3"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

unmanagedResourceDirectories in Compile <++= baseDirectory { base =>
    Seq( base / "src/main/webapp" )
}

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/"
)

libraryDependencies ++= {
    val akkaVersion = "2.2.3"
    val sprayVersion = "1.2-RC4"
    Seq(
      "io.spray"            %   "spray-can"     % sprayVersion,
      "io.spray"            %   "spray-routing" % sprayVersion,
      "io.spray"            %   "spray-testkit" % sprayVersion,
      "io.spray"            %%  "spray-json"    % "1.2.5",
      "com.typesafe.akka"   %%  "akka-actor"    % akkaVersion,
      "org.specs2"          %%  "specs2"        % "1.13" % "test",
      "org.scalatest"       %   "scalatest_2.10" % "2.0.M5b" % "test"
    )
}

seq(Revolver.settings: _*)

seq(Twirl.settings: _*)

