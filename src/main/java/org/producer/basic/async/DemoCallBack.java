package org.producer.basic.async;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

class DemoCallBack implements Callback {
    private final String message;

    public DemoCallBack(String message) {
        this.message = message;
    }

    @Override
    public void onCompletion(RecordMetadata metadata, Exception e) {
        System.out.println("Send OK in Callback");
        System.out.printf("Record values = %s partition = %d offset = %d ", message, metadata.partition(), metadata.offset());

    }
}
