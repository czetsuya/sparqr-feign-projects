FROM amazoncorretto:17.0.3-alpine

ENV TZ="UTC"

ADD app/target/*.jar /application/
ADD docker/docker-entrypoint.sh /

RUN set -xe; \
    adduser -D -u 2001 app.user; \
    chmod a+x /docker-entrypoint.sh

USER app.user

EXPOSE 8000

ENTRYPOINT [ "/docker-entrypoint.sh" ]