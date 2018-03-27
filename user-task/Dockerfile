FROM openjdk:8-alpine

RUN apk update
RUN apk upgrade
RUN apk add --update tzdata
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/Shanghai" > /etc/timezone
RUN apk del tzdata

RUN apk add --update tini

ENTRYPOINT ["/sbin/tini", "--"]

COPY target/BUILD_PACKAGE_FILE_NAME /opt/workspace/

WORKDIR /opt/workspace/

RUN mkdir log

VOLUME /opt/workspace/log

CMD ["java", "-Xmx128m", "-jar", "BUILD_PACKAGE_FILE_NAME"]