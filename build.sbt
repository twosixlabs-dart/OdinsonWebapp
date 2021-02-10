import play.sbt.PlayImport

organization in ThisBuild := "org.clulab"
name := "OdinsonWebapp"

scalaVersion in ThisBuild := "2.12.7"

resolvers in ThisBuild ++= Seq("Maven Central" at "https://repo1.maven.org/maven2/",
                                "Clulab Artifactory" at "http://artifactory.cs.arizona.edu:8081/artifactory/sbt-release", // processors-models
                                "Local Ivy Repository" at s"file://${System.getProperty("user.home")}/.ivy2/local/default")

disablePlugins(sbtassembly.AssemblyPlugin)

val procVer = "8.2.3"
val odinsonVer = "0.3.0-SNAPSHOT"
val playVersion = "2.6.6"

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
  .enablePlugins(PlayScala)
  .settings(
    libraryDependencies ++= Seq(
        //      "com.typesafe.play" %% "play-server" % playVersion,
        //      "com.typesafe.play" %% "play-akka-http-server" % playVersion,
        //      "com.typesafe.play" %% "play-guice" % playVersion,
        //      "com.typesafe.play" %% "play-json" % playVersion,
      PlayImport.guice,
      "com.google.inject" % "guice" % "4.1.0", // compat w/ play framework: https://github.com/playframework/playframework/blob/2.6.6/framework/project/Dependencies.scala#L125
      "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
   ),
//    mainClass in assembly := Some("play.core.server.DevServerStart"),
    test in assembly := {},
    assemblyMergeStrategy in assembly := {
        case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
        case PathList("META-INF", "*.SF") => MergeStrategy.discard
        case PathList("META-INF", "*.DSA") => MergeStrategy.discard
        case PathList("META-INF", "*.RSA") => MergeStrategy.discard
        case PathList("META-INF", "*.DEF") => MergeStrategy.discard
        case PathList("*.SF") => MergeStrategy.discard
        case PathList("*.DSA") => MergeStrategy.discard
        case PathList("*.RSA") => MergeStrategy.discard
        case PathList("*.DEF") => MergeStrategy.discard
        case PathList("META-INF", "services", "org.apache.lucene.codecs.PostingsFormat") => MergeStrategy.filterDistinctLines
        case PathList("META-INF", "services", "com.fasterxml.jackson.databind.Module") => MergeStrategy.filterDistinctLines
        case PathList("META-INF", "services", "javax.xml.transform.TransformerFactory") => MergeStrategy.first // or last or both?
        case PathList("reference.conf") => MergeStrategy.concat
        case PathList("logback.xml") => MergeStrategy.first
        case _ => MergeStrategy.last
    }
)
