package org.producer.basic;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProducerSync {

    private final static String BOOTSTRAP_SERVERS = "ec2-18-135-249-38.eu-west-2.compute.amazonaws.com:9092"; // this could be a list
    private final static String TOPIC = "wordcount-in2";
    // use String datatype for both key and value
    private static Producer<String,String> createProducer(){

        Properties properties = new Properties();
        // put bootstrap servers
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVERS);

        // put key serializer
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // put value serializer
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.ACKS_CONFIG, "1");
        return new KafkaProducer<>(properties);
    }

    static void runProducer(){
        final Producer<String, String> producer = createProducer();

        // the actual data to send is in ProducerRecord
        //                      key, value
        final ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC,"Late night diner");
        try {
            for(int i=0;i<10;i++) {
                RecordMetadata metadata = producer.send(record).get();

                System.out.println("Send OK");
                System.out.printf("Record values = %s partition = %d offset = %d ", record.value(), metadata.partition(), metadata.offset());
            }
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
