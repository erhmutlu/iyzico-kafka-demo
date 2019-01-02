package org.erhanmutlu.payment.consumer.infrastructure.serializer;

import org.apache.kafka.common.header.Headers;
import org.erhanmutlu.payment.consumer.model.CreatePaymentRequestMessage;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class CreatePaymentRequestMessageDeserializer extends JsonDeserializer<CreatePaymentRequestMessage>{

    @Override
    public CreatePaymentRequestMessage deserialize(String topic, Headers headers, byte[] data) {
        return super.deserialize(topic, headers, data);
    }
}
