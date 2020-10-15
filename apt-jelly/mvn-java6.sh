#!/bin/sh
#
# references:
#
#   - https://maven.apache.org/surefire/maven-surefire-plugin/examples/debugging.html
#
# usage examples:
#
#   ./mvn-java6.sh clean compile
#
#   ./mvn-java6.sh package
#
#   ./mvn-java6.sh -N clean install
#
#   ./mvn-java6.sh -rf freemarker -Dmaven.surefire.debug -Dtest=net.sf.jelly.apt.freemarker.transforms.TestForAllTypesTransform test
#
#   ./mvn-java6.sh -rf freemarker -Dmaven.surefire.debug="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=4000 -Xnoagent -Djava.compiler=NONE" -Dtest=net.sf.jelly.apt.freemarker.transforms.TestForAllTypesTransform test
#

JAVA_HOME=${JAVA_HOME:-${HOME}/Ephemeral/bin/jdk1.6.0_45}
MAVEN_HOME=${MAVEN_HOME:-${HOME}/Ephemeral/bin/apache-maven-3.1.1}
PATH=${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${PATH}

JDK6_HOME=${JAVA_HOME}
export JDK6_HOME

#exec mvnDebug -DforkCount=0 "$@"
exec mvn "$@"
