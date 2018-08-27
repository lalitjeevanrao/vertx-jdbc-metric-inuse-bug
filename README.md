# vertx-jdbc-metric-inuse-bug
A simple reproducer to demonstrate negative value in vertx-dropwizard metric; in-use count

The console reporter will start reporting metrics in console at 10 seconds frequency.

Under -- Counters --, check "vertx.pools.datasource.DEFAULT_DS.in-use".

The value will be -1.

To run the project, execute the following steps

1. cd vertx-jdbc-metric-inuse-bug
2. ./gradlew clean build
3. java -jar build/libs/vertx-jdbc-metric-inuse-bug.jar
