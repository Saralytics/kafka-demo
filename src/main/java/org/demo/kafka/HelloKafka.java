package org.demo.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class HelloKafka {

    private final static String BOOTSTRAP_SERVERS = "192.168.143.134:9092";
    private final static String TOPIC = "canicreatenewtopic";
    // use String datatype for both key and value
    private static Producer<String,String> createProducer(){

        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVERS);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.ACKS_CONFIG, "1");
        return new KafkaProducer<>(properties);
    }

    static void runProducer(){
        final Producer<String, String> producer = createProducer();
        final ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC,"888");
        try {
            RecordMetadata metadata = producer.send(record).get();

            System.out.println("Send OK");
            System.out.printf("Record values = %s partition = %d offset = %d ",record.value(), metadata.partition(), metadata.offset());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } finally{
            producer.flush();
            producer.close();
        }
    }

    public static void main(String[] args) {
        System.out.println("sENDING SOMETHING");
        runProducer();
    }
}
