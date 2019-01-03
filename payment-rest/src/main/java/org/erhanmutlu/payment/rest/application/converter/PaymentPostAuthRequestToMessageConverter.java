package org.erhanmutlu.payment.rest.application.converter;

import org.erhanmutlu.payment.common.kafka.PaymentPostAuthRequestMessage;
import org.erhanmutlu.payment.rest.application.request.PaymentPostAuthRequest;
import org.springframework.stereotype.Component;

@Component
public class PaymentPostAuthRequestToMessageConverter {

    public PaymentPostAuthRequestMessage convert(PaymentPostAuthRequest paymentPostAuthRequest) {
        PaymentPostAuthRequestMessage paymentPostAuthRequestMessage = new PaymentPostAuthRequestMessage();
        paymentPostAuthRequestMessage.setUniqueId(paymentPostAuthRequest.getIyzicoPaymentUniqueIdentifier());
        paymentPostAuthRequestMessage.setApiKey(paymentPostAuthRequest.getApiKey());
        paymentPostAuthRequestMessage.setSecretKey(paymentPostAuthRequest.getSecretKey());
        paymentPostAuthRequestMessage.setPrice(paymentPostAuthRequest.getPrice());
        return paymentPostAuthRequestMessage;
    }
}
