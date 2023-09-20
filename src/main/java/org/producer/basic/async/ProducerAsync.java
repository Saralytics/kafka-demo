package org.producer.basic.async;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerAsync {

    private final static String BOOTSTRAP_SERVERS = "192.168.143.134:9092"; // this could be a list
    private final static String TOPIC = "java-api";
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
        final ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC,"888");
        try {
            for(int i=0; i<=1000; i++) {
                producer.send(record, new DemoCallBack("888"));
            }
            System.out.println("Finish!");
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


