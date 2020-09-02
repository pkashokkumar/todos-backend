#Base Image for this is :
FROM openjdk:11.0.4-jre-slim

EXPOSE 5555

ENV SVC_HOME=/opt/todo
RUN mkdir -p $SVC_HOME
#RUN mkdir -p $SVC_HOME/lib

# copying all libs separately reduces the upload / download time and the service footprint size
# this occurs instead of bundling everything in fat jar

#RUN mkdir -p target/dependency && (cd target/dependency; jar -xf *.jar)
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib $SVC_HOME/lib
COPY ${DEPENDENCY}/META-INF $SVC_HOME/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes $SVC_HOME


WORKDIR $SVC_HOME
ENTRYPOINT ["java","-cp",".:./lib/*","xyz.cloudease.todo.TodoApplication"]
