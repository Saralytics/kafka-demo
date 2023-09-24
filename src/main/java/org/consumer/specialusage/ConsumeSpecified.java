package org.consumer.specialusage;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.internals.Topic;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.consumer.basic.MultipleConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConsumeSpecified {
    private final static String BOOTSTRAP_SERVERS = "192.168.143.134:9092";
    private final static String TOPIC = "consumer01";
    private final static String GROUPID = "group02"; // create a new group here so we can mimic having a lot of data that we don't want to consume
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
        return new KafkaConsumer(properties);
    }

    static void runConsumer(){
        final Consumer<String,String> consumer = createConsumer();
//        Instead of subscribe, we assign where the consumer reads
//        consumer.subscribe(Collections.singletonList(TOPIC));
//        The below tells the consumer to read from TOPIC, partition 0, offset 12 (including and after 12)
        consumer.assign(Collections.singleton(new TopicPartition(TOPIC, 0)));
        long offsetToRead = 12L;
        TopicPartition partitionToRead = new TopicPartition(TOPIC, 0);
        consumer.seek(partitionToRead,offsetToRead);

        // additionally, we can specify after which timestamp
        Map<TopicPartition, Long> timestampToSearch = new HashMap<TopicPartition,Long>();
        timestampToSearch.put(partitionToRead,1695236353011L); // tool to search for the nearest offset at this timestamp

        Map<TopicPartition, OffsetAndTimestamp> outoffsets = consumer.offsetsForTimes(timestampToSearch);
        Long seekOffset = outoffsets.get(partitionToRead).offset();
        consumer.seek(partitionToRead, seekOffset);

        try{
            while (true){
                ConsumerRecords<String, String> records = consumer.poll(100); // ms
                for (ConsumerRecord<String, String > record: records) {
                    log.info("Topic: " + record.topic() + " value: " + record.value() + " partition " + record.partition() + " offset " + record.offset() + " ts " + record.timestamp());
                }
            }
        } finally {
            consumer.close();
            log.info("Consumer program closed");
        }
    }

    public static void main(String[] args) {
        runConsumer();
    }
}
