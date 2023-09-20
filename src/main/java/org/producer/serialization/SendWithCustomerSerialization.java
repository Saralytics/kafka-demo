package org.producer.serialization;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class SendWithCustomerSerialization {

    private final static String BOOTSTRAP_SERVERS = "192.168.143.134:9092"; // this could be a list
    private final static String TOPIC = "customerpartition";
    // use String datatype for both key and value
    private static Producer<String,Customer> createProducer(){

        Properties properties = new Properties();
        // put bootstrap servers
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVERS);

        // put key serializer
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // put value serializer
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CustomerSerializer.class.getName());
        properties.put(ProducerConfig.ACKS_CONFIG, "1");
        return new KafkaProducer<>(properties);
    }

    static void runProducer(Customer customer1){
        final Producer<String, Customer> producer = createProducer();

        // the actual data to send is in ProducerRecord
        //                      key, value
        final ProducerRecord<String, Customer> record = new ProducerRecord<>(TOPIC,customer1);
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

    public static void main(String[] args) throws ParseException {
        System.out.println("Sending a customer info");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-mm-dd");
        Date date = sf.parse("2018-01-01");
        Customer data = new Customer("Jackson",100,"Al reem island",date);
        runProducer(data);
    }
}
