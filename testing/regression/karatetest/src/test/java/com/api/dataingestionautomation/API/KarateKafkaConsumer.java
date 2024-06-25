package com.api.dataingestionautomation.API;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class KarateKafkaConsumer {
    private KafkaConsumer<String, String> consumer;

    public KarateKafkaConsumer(String bootstrapServers, String groupId) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", groupId);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "latest");

        consumer = new KafkaConsumer<>(props);
    }

    public Map<String, String> readLatestFromTopics(String... topics) {
        Map<String, String> latestRecords = new HashMap<>();
        Duration duration = Duration.ofMillis(500);

        for (String topic : topics) {
            List<TopicPartition> topicPartitions = getTopicPartitions(topic);
            consumer.assign(topicPartitions);
            seekToEndAndFetchLatestRecord(topicPartitions, latestRecords, duration);
        }

        consumer.close();
        return latestRecords;
    }

    private List<TopicPartition> getTopicPartitions(String topic) {
        Set<TopicPartition> topicPartitions = consumer.partitionsFor(topic).stream()
                .map(partitionInfo -> new TopicPartition(topic, partitionInfo.partition()))
                .collect(Collectors.toSet());
        return new ArrayList<>(topicPartitions);
    }

    private void seekToEndAndFetchLatestRecord(List<TopicPartition> topicPartitions, Map<String, String> latestRecords, Duration duration) {
        Map<TopicPartition, Long> endOffsets = consumer.endOffsets(topicPartitions);
        for (TopicPartition topicPartition : topicPartitions) {
            long lastOffset = endOffsets.get(topicPartition) - 1;
            if (lastOffset >= 0) {
                consumer.seek(topicPartition, lastOffset);
                ConsumerRecords<String, String> records = consumer.poll(duration);
                if (!records.isEmpty()) {
                    ConsumerRecord<String, String> lastRecord = records.iterator().next();
                    latestRecords.put(topicPartition.topic(), lastRecord.value());
                }
            }
        }
    }
}
