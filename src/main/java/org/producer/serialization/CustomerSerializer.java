package org.producer.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class CustomerSerializer implements Serializer<Customer> {
    @Override
    public byte[] serialize(String topic, Customer customer) {
        byte[] retVal = null;
        ObjectMapper objectMapper= new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(customer).getBytes();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return retVal;
    }
}
