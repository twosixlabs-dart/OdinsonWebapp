FROM openjdk:8

ENV APP_DIR /opt/app

ENV JAVA_OPTS="-Dplay.http.secret.key='QCY?tAnfk?aZ?iwrNwnxIlR6CTf:G3gf:90Latabg@5241AB`R5W:1uDFN];Ik@n'"

RUN mkdir /opt/app

WORKDIR $APP_DIR

COPY ./webapp/target/scala-*/*assembly*.jar $APP_DIR/app.jar

RUN chmod -R 755 /opt/app

ENTRYPOINT java $JAVA_OPTS -jar app.jar