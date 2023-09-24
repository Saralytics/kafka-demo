package org.consumer.basic;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

public class MultipleTopic {
    private final static String BOOTSTRAP_SERVERS = "192.168.143.134:9092";
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
        return new KafkaConsumer(properties);
    }

    static void runConsumer(){
        final Consumer<String,String> consumer = createConsumer();
        // subscribe to an array of topics
        // can use Pattern.compile("pattern*") regex
        consumer.subscribe(Arrays.asList("consumer01","consumer02"));
        try{
            while (true){
                ConsumerRecords<String, String> records = consumer.poll(100); // ms
                for (ConsumerRecord<String, String > record: records) {
                    log.info("Topic: " + record.topic() + " value: " + record.value() + " partition " + record.partition() + " offset " + record.offset());
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
