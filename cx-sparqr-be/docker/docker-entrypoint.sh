#!/bin/sh -e

cd /application

JMX_OPTS=""
JMX_PORT=9875

if [ "$JMX_ENABLED" = "TRUE" ] || [ "$JMX_ENABLED" = "true" ]; then

    JMX_OPTS="-Dcom.sun.management.jmxremote=true \
              -Dcom.sun.management.jmxremote.port=${JMX_PORT} \
              -Dcom.sun.management.jmxremote.rmi.port=${JMX_PORT} \
              -Dcom.sun.management.jmxremote.local.only=false \
              -Dcom.sun.management.jmxremote.authenticate=false \
              -Dcom.sun.management.jmxremote.ssl=false \
              -Djava.rmi.server.hostname=localhost"

fi

JAVA_OPTS="${JAVA_OPTS} ${JMX_OPTS}"

exec java -jar $JAVA_OPTS *.jar "$@"