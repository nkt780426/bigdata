import pandas as pd
from kafka import KafkaProducer
import json
import logging
import time

# Thiết lập logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s %(levelname)s:%(name)s:%(message)s')
logger = logging.getLogger('kafka_producer')

# Đọc dữ liệu từ file excel về pandas
excel_file = './sample_data/SuperStoreUS-2015.xlsx'
df = pd.read_excel(excel_file)
df['Order Date'] = df['Order Date'].astype(str)
df['Ship Date'] = df['Ship Date'].astype(str)

# Group các order_detail lại theo order_id
grouped_data = df.groupby('Order ID')

# Kafka broker và topic
bootstrap_servers = 'kafka1:9192,kafka2:9192,kafka3:9192'
topic = 'orders'

# Khởi tạo Kafka producer
producer = KafkaProducer(
    bootstrap_servers=bootstrap_servers,
    key_serializer=lambda k: str(k).encode('utf-8'),
    value_serializer=lambda v: json.dumps(v).encode('utf-8'),
    max_request_size=104857600,  # 100 MB
    acks='all',  # Đảm bảo tất cả các replica đã nhận tin nhắn
    linger_ms=5,  # Thời gian chờ trước khi gửi tin nhắn
    batch_size=16384  # Kích thước batch gửi tin nhắn
)

# Gửi các sự kiện tới Kafka
for order_id, group in grouped_data:
    # Chuyển đổi group thành các dictionary, mỗi dictionary là 1 order_detail
    order_details = group.drop(columns=['Order ID']).to_dict(orient='records')
    
    record = {
        "orderId": order_id,
        "orderDate": order_details[0]["Order Date"],
        "customer": {
            "id": order_details[0]["Customer ID"],
            "name": order_details[0]["Customer Name"],
            "segment": order_details[0]["Customer Segment"],
            "country": order_details[0]["Country"],
            "region": order_details[0]["Region"],
            "stateOrProvince": order_details[0]["State or Province"],
            "city": order_details[0]["City"],
            "postalCode": order_details[0]["Postal Code"]
        },
        "orderDetails": [
            {
                "rowId": row["Row ID"],
                "orderPriority": row["Order Priority"],
                "discount": row["Discount"],
                "unitPrice": row["Unit Price"],
                "shippingCost": row["Shipping Cost"],
                "shipMode": row["Ship Mode"],
                "product": {
                    "category": row["Product Category"],
                    "subCategory": row["Product Sub-Category"],
                    "name": row["Product Name"],
                    "container": row["Product Container"],
                    "baseMargin": row["Product Base Margin"]
                },
                "shipDate": row["Ship Date"],
                "profit": row["Profit"],
                "quantityOrderedNew": row["Quantity ordered new"],
                "sales": row["Sales"]
            }
            for row in order_details
        ]
    }
    
    # Log thông tin gửi
    formatted_record = json.dumps(record, indent=2)
    logger.info(f'Sending record with order_id {order_id}:\n{formatted_record}')
    
    # Sử dụng order_id làm key để đảm bảo các order_detail cùng order_id được gửi tới cùng partition
    producer.send(topic, key=str(order_id).encode('utf-8'), value=record)
    
    time.sleep(0.2)

# Đảm bảo tất cả tin nhắn đã được gửi và đóng Kafka producer
producer.flush()
producer.close()
