# Flink Connector for Dual Doris Clusters

This project is a custom implementation of a Flink Connector, which has been forked from https://github.com/apache/doris-flink-connector tag 113_2.12-1.0.3. The main goal of this project is to extend the capabilities of the original implementation by enabling data to be simultaneously written to two Doris clusters, thereby achieving synchronization between a primary and a secondary cluster.

## Features

* Supports writing data to two Doris clusters simultaneously

## Usage Example

This is an example of how to use the Flink Connector for Multiple Doris Clusters in your Flink application:

```sql
%flink.ssql()
drop table if exists database.table;
create table database.table (
    field tyeps,
    ...
) with (
    'connector' = 'doris-dual-cluster',
    'master.fenodes' = 'xx.x.xx.xx:8030',
    'slave.fenodes' = 'xx.x.xx.xx:8030',
    'table.identifier' = 'your_doris.table',
    'username' = 'xxx',
    'password' = 'xxx'
);
```

If both `master.fenodes` and `slave.fenodes` are configured at the same time, data will be written to both clusters simultaneously. If only one of them is configured, data will only be written to one cluster.

The Flink log is as follows:
```shell
2023-05-10 10:13:35,579 INFO  org.apache.doris.flink.table.DorisStreamLoad                 [] - Master Streamload Response:{"status":200,"respMsg":"OK","respContent":"{\n    \"TxnId\": 14980667,\n    \"Label\": \"flink_connector_20230510_101335_c0d9aa6b75f24c159e639613b1f8add8\",\n    \"TwoPhaseCommit\": \"false\",\n    \"Status\": \"Success\",\n    \"Message\": \"OK\",\n    \"NumberTotalRows\": 7,\n    \"NumberLoadedRows\": 7,\n    \"NumberFilteredRows\": 0,\n    \"NumberUnselectedRows\": 0,\n    \"LoadBytes\": 1126,\n    \"LoadTimeMs\": 22,\n    \"BeginTxnTimeMs\": 0,\n    \"StreamLoadPutTimeMs\": 0,\n    \"ReadDataTimeMs\": 0,\n    \"WriteDataTimeMs\": 4,\n    \"CommitAndPublishTimeMs\": 15\n}\n"}

2023-05-10 10:13:35,609 INFO  org.apache.doris.flink.table.DorisStreamLoad                 [] - Slave Streamload Response:{"status":200,"respMsg":"OK","respContent":"{\n    \"TxnId\": 417358,\n    \"Label\": \"flink_connector_20230510_101335_a7276572fe00417291dc42305dda41e4\",\n    \"TwoPhaseCommit\": \"false\",\n    \"Status\": \"Success\",\n    \"Message\": \"OK\",\n    \"NumberTotalRows\": 7,\n    \"NumberLoadedRows\": 7,\n    \"NumberFilteredRows\": 0,\n    \"NumberUnselectedRows\": 0,\n    \"LoadBytes\": 1126,\n    \"LoadTimeMs\": 28,\n    \"BeginTxnTimeMs\": 0,\n    \"StreamLoadPutTimeMs\": 0,\n    \"ReadDataTimeMs\": 0,\n    \"WriteDataTimeMs\": 5,\n    \"CommitAndPublishTimeMs\": 21\n}\n"}
```