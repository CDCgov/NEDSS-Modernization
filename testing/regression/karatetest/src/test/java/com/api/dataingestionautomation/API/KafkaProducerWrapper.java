package com.api.dataingestionautomation.API;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;

public class KafkaProducerWrapper {
    private final Producer<String, String> producer;

    public KafkaProducerWrapper(String bootstrapServers) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(properties);
    }

    public Future<RecordMetadata> send(String topic, String key, String value) {
        return producer.send(new ProducerRecord<>(topic, key, value));
    }

    public Future<RecordMetadata> send(String topic, String value) {
        return producer.send(new ProducerRecord<>(topic, null, value));
    }

    public void close() {
        producer.close();
    }
}
