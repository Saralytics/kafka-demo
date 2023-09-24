package org.consumer.deserialize;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.consumer.basic.MultipleConsumer;
import org.producer.serialization.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;

public class ConsumerWithDeserializer {
    private final static String BOOTSTRAP_SERVERS = "192.168.143.134:9092";
    private final static String TOPIC = "java-api";
    private final static String GROUPID = "group01";
    static Logger log = LoggerFactory.getLogger(MultipleConsumer.class.getName());

    // Create a consumer
    private static Consumer<String, Customer> createConsumer() {
        // configurations
        Properties properties = new Properties();
        // put bootstrap servers
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVERS);
        // Key deserializer
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // put value deserializer -> change this to Custom-made deserializer, that's it!
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,GROUPID);
        return new KafkaConsumer(properties);
    }

    static void runConsumer(){
        final Consumer<String,Customer> consumer = createConsumer();
        // subscribe to only 1 topic
        consumer.subscribe(Collections.singletonList(TOPIC));
        try{
            while (true){
                ConsumerRecords<String, Customer> records = consumer.poll(100); // ms
                for (ConsumerRecord<String, Customer > record: records) {
                    log.info("Topic: " + record.topic() + " value: " + record.value().getName() + " partition " + record.partition() + " offset " + record.offset());
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
