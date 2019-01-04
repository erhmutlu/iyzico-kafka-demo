package org.erhanmutlu.payment.rest.application.response.mapper;

import org.erhanmutlu.payment.common.kafka.PaymentAuthRequestMessage;
import org.erhanmutlu.payment.common.kafka.PaymentPostAuthRequestMessage;
import org.erhanmutlu.payment.common.kafka.PaymentPreAuthRequestMessage;
import org.erhanmutlu.payment.rest.application.response.CreatePaymentResponse;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
public class CreatePaymentResponseMapper {

    public CreatePaymentResponse prepareResponse(PaymentAuthRequestMessage paymentAuthRequestMessage){
        CreatePaymentResponse createPaymentResponse = newPendingResponse();
        createPaymentResponse.setIyzicoPaymentUniqueIdentifier(paymentAuthRequestMessage.getUniqueId());
        createPaymentResponse.setConversationId(paymentAuthRequestMessage.getConversationId());
        return createPaymentResponse;
    }

    public CreatePaymentResponse prepareResponse(PaymentPreAuthRequestMessage paymentPreAuthRequestMessage){
        CreatePaymentResponse createPaymentResponse = newPendingResponse();
        createPaymentResponse.setIyzicoPaymentUniqueIdentifier(paymentPreAuthRequestMessage.getUniqueId());
        createPaymentResponse.setConversationId(paymentPreAuthRequestMessage.getConversationId());
        return createPaymentResponse;
    }

    public CreatePaymentResponse prepareResponse(PaymentPostAuthRequestMessage paymentPostAuthRequestMessage){
        CreatePaymentResponse createPaymentResponse = newPendingResponse();
        createPaymentResponse.setIyzicoPaymentUniqueIdentifier(paymentPostAuthRequestMessage.getUniqueId());
        return createPaymentResponse;
    }

    private CreatePaymentResponse newPendingResponse(){
        CreatePaymentResponse createPaymentResponse = new CreatePaymentResponse();
        createPaymentResponse.setStatus("pending");
        createPaymentResponse.setSystemTime(Clock.systemUTC().millis());
        return createPaymentResponse;
    }
}
