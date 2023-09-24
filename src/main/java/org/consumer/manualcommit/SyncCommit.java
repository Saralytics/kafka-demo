package org.consumer.manualcommit;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.consumer.basic.MultipleConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SyncCommit {
    private final static String BOOTSTRAP_SERVERS = "192.168.143.134:9092";
    private final static String TOPIC = "consumer01";
    private final static String GROUPID = "group01";
    static Logger log = LoggerFactory.getLogger(MultipleConsumer.class.getName());

    // Create a consumer
    private static Consumer<String, String> createConsumer() {
        // configurations
        Properties properties = new Properties();
        // put bootstrap servers
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVERS);
        // Key deserializer
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // put value deserializer
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,GROUPID);
        properties.put("enable.auto.commit", "false");
        return new KafkaConsumer(properties);
    }
    private static Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<TopicPartition, OffsetAndMetadata>();


    static void runConsumer(){
        final Consumer<String,String> consumer = createConsumer();

        // subscribe to only 1 topic
        consumer.subscribe(Collections.singletonList(TOPIC));
        try{
            while (true){
                ConsumerRecords<String, String> records = consumer.poll(100); // ms
                for (ConsumerRecord<String, String > record: records) {
                    currentOffsets.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset(), "no metadata"));
                    log.info("Topic: " + record.topic() + " value: " + record.value() + " partition " + record.partition() + " offset " + record.offset());
                    // Note that in this case, there is one commit for every record
                    consumer.commitSync(currentOffsets);
                }
            }
        } finally {
            consumer.close();
            log.info("Consumer program closed");
        }
    }

    public static void main(String[] args)
    {
        runConsumer();
    }
}
