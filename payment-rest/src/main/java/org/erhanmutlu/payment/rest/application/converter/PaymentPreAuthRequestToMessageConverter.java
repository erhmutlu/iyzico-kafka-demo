package org.erhanmutlu.payment.rest.application.converter;

import org.erhanmutlu.payment.common.kafka.PaymentPreAuthRequestMessage;
import org.erhanmutlu.payment.rest.application.request.PaymentPreAuthRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentPreAuthRequestToMessageConverter {

    public PaymentPreAuthRequestMessage convert(PaymentPreAuthRequest paymentPreAuthRequest) {
        PaymentPreAuthRequestMessage paymentPreAuthRequestMessage = new PaymentPreAuthRequestMessage();
        paymentPreAuthRequestMessage.setUniqueId(UUID.randomUUID().toString());
        paymentPreAuthRequestMessage.setConversationId(paymentPreAuthRequest.getConversationId());
        paymentPreAuthRequestMessage.setApiKey(paymentPreAuthRequest.getApiKey());
        paymentPreAuthRequestMessage.setSecretKey(paymentPreAuthRequest.getSecretKey());
        paymentPreAuthRequestMessage.setPrice(paymentPreAuthRequest.getPrice());
        return paymentPreAuthRequestMessage;
    }
}
