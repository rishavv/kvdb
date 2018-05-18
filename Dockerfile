FROM java:8-jre

WORKDIR /opt/app/kvdb

ENV KVDB_NUM_OF_NODES 2

ENV KVDB_NODE_IDX 1

ENV KVDB_MASTER_NODE_PORT 6600

ENV KVDB_HOST_NAME 127.0.0.1

ENV APP_SERVER_PORT 6601

COPY target/kvdb-0.1.jar /opt/app/kvdb/kvdb-0.1.jar

EXPOSE ${APP_SERVER_PORT}

CMD ["java", "-jar", "kvdb-0.1.jar"]