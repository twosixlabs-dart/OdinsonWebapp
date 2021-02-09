organization in ThisBuild := "org.clulab"
name := "OdinsonWebapp"

scalaVersion in ThisBuild := "2.12.7"

resolvers +=  "Artifactory" at "http://artifactory.cs.arizona.edu:8081/artifactory/sbt-release" // processors-models

disablePlugins(sbtassembly.AssemblyPlugin)

val procVer = "8.2.3"
val odinsonVer = "0.3.0-SNAPSHOT"

lazy val root = (project in file(".")).aggregate(core, webapp)

lazy val core = (project in file("core"))
  .disablePlugins(sbtassembly.AssemblyPlugin)
  .settings(
    libraryDependencies ++= Seq(
      "org.clulab"    %% "processors-main"          % procVer,
      "org.clulab"    %% "processors-corenlp"       % procVer,
      "ai.lum"        %% "odinson-core"             % odinsonVer,
      "ai.lum"        %% "odinson-extra"            % odinsonVer,
      "ai.lum"        %% "common"                   % "0.0.10",
      "com.lihaoyi"   %% "ujson"                    % "0.7.1",
      "com.lihaoyi"   %% "upickle"                  % "0.7.1")
  )

lazy val webapp = (project in file("webapp"))
  .dependsOn(core)
  .settings(
    libraryDependencies ++= Seq(
      "com.google.inject" % "guice" % "4.1.0", // compat w/ playframework: https://github.com/playframework/playframework/blob/2.6.6/framework/project/Dependencies.scala#L125
      "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
    ),
    mainClass in assembly := Some("play.core.server.ProdServerStart"),
    test in assembly := {}
)
