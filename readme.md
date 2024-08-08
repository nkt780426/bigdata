# Mục đích project
Dựng 1 lakehouse và thực hiện batch/streaming processing
# Dựng project
1. Install dependence and docker image
```sh
find . -type f -name "*.sh" -exec chmod +x {} \;
cd script/1-build/
./start.sh
```
2. Start streaming processing
Submit flinkjob lên cụm flink
Sau khi đã submit job, chạy lệnh
```sh
cd ../2-streaming
./start-stream.sh
```
Đợi khoảng 5p cho dữ liệu đổ hoàn toàn vào flink hủy flink job bạn sẽ thấy dữ liệu được trích xuất được đổ vào minio dưới dạng iceberg
```sh
./stop-stream.sh
```
3. Start batch processing
Bạn có thể lựa chọn spark hoặc trino để xử lý batch. Trong project này, mình sử dụng spark
```sh
cd ../3-batch
./start-spark.sh
```
Mở jupyter notebook lên và tương tác với cụm spark.
Nếu bạn muốn sử dụng trino. Chạy lệnh sau
```sh
./start-trino
```
4. Dừng project
```sh
./stop-all.sh
```