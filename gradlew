#!/bin/sh

# Minimal Gradle wrapper launcher. The tracked JAR and properties select the
# project Gradle version, so contributors do not need a global installation.
APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd -P)
exec java ${JAVA_OPTS} ${GRADLE_OPTS} "-Dorg.gradle.appname=$(basename "$0")" \
  -classpath "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" \
  org.gradle.wrapper.GradleWrapperMain "$@"
