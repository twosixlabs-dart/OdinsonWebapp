FROM ysihaoy/scala-play:2.12.2-2.6.0-sbt-0.13.15

ENV SCALA_VERSION '2.12'
EXPOSE 9000

COPY ./target/scala-$SCALA_VERSION/*assembly*.jar /
