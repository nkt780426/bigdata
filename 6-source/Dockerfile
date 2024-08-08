# Specify the base image with Python and necessary libraries
FROM python:3.10-slim

# Set the working directory inside the container
WORKDIR /app

# Copy all files to the working directory
COPY . .

# Install the required Python packages
RUN pip install --no-cache-dir -r requirements.txt

# Define the command to run your Kafka producer script
CMD ["python", "kafka_producer.py"]
