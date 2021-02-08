organization := "org.clulab"
name := "OdinsonWebapp"


resolvers +=  "Artifactory" at "http://artifactory.cs.arizona.edu:8081/artifactory/sbt-release" // processors-models

val procVer = "8.2.3"
val odinsonVer = "0.3.0-SNAPSHOT"

lazy val root = (project in file(".")).aggregate(core, webapp)
lazy val core = (project in file("core")).settings(
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
  .enablePlugins( PlayScala )
  .dependsOn( core )
