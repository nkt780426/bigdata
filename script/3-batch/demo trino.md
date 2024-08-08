# Nessie
pip install pynessie
export NESSIE_SERVER=http://localhost:19120/api/v2
nessie branch trino

# Trino
## Truy cập vào trino server
docker ps | grep coordinator

docker exec -it 7eb2511e4129 bash

trino

clear;
## Tương tác với trino server
```sh
SHOW CATALOGS;

CREATE SCHEMA nessie.trino;

CREATE TABLE nessie.trino.example (
    c1 INTEGER,
    c2 DATE,
    c3 DOUBLE
)
WITH(
    format = 'PARQUET',
    partitioning = ARRAY['c1', 'c2'],
    sorted_by = ARRAY['c3'],
    location = 's3a://test'
);

INSERT INTO nessie.trino.example (c1, c2, c3)
VALUES
    (1, DATE '2024-07-26', 10.5),
    (2, DATE '2024-07-27', 20.5),
    (3, DATE '2024-07-28', 30.5);
```