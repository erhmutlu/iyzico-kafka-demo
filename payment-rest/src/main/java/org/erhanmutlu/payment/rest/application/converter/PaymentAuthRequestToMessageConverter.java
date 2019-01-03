package org.erhanmutlu.payment.rest.application.converter;

import org.erhanmutlu.payment.common.kafka.PaymentAuthRequestMessage;
import org.erhanmutlu.payment.rest.application.request.PaymentAuthRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentAuthRequestToMessageConverter {

    public PaymentAuthRequestMessage convert(PaymentAuthRequest paymentAuthRequest) {
        PaymentAuthRequestMessage paymentAuthRequestMessage = new PaymentAuthRequestMessage();
        paymentAuthRequestMessage.setUniqueId(UUID.randomUUID().toString());
        paymentAuthRequestMessage.setConversationId(paymentAuthRequest.getConversationId());
        paymentAuthRequestMessage.setApiKey(paymentAuthRequest.getApiKey());
        paymentAuthRequestMessage.setSecretKey(paymentAuthRequest.getSecretKey());
        paymentAuthRequestMessage.setPrice(paymentAuthRequest.getPrice());
        return paymentAuthRequestMessage;
    }
}
