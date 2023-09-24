package org.consumer.deserialize;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.producer.serialization.Customer;

import java.io.IOException;

public class CustDeserializer implements Deserializer<Customer> {
    @Override
    public Customer deserialize(String s, byte[] bytes) {
        ObjectMapper objectMapper = new ObjectMapper();
        Customer customer = null;
        try {
            customer = objectMapper.readValue(bytes, Customer.class);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return customer;
    }
}
