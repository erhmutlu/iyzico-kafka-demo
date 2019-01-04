package org.erhanmutlu.payment.rest.application.service;

import org.erhanmutlu.payment.common.kafka.PaymentAuthRequestMessage;
import org.erhanmutlu.payment.common.kafka.PaymentPostAuthRequestMessage;
import org.erhanmutlu.payment.common.kafka.PaymentPreAuthRequestMessage;
import org.erhanmutlu.payment.rest.application.converter.PaymentAuthRequestToMessageConverter;
import org.erhanmutlu.payment.rest.application.converter.PaymentPostAuthRequestToMessageConverter;
import org.erhanmutlu.payment.rest.application.converter.PaymentPreAuthRequestToMessageConverter;
import org.erhanmutlu.payment.rest.application.request.PaymentPostAuthRequest;
import org.erhanmutlu.payment.rest.application.request.PaymentPreAuthRequest;
import org.erhanmutlu.payment.rest.application.response.CreatePaymentResponse;
import org.erhanmutlu.payment.rest.application.response.mapper.CreatePaymentResponseMapper;
import org.erhanmutlu.payment.rest.infrastructure.kafka.MessageProducerService;
import org.erhanmutlu.payment.rest.application.request.PaymentAuthRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class PaymentRequestMessagePublisherService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentRequestMessagePublisherService.class);

    @Value("${kafka.topics.payment:t_payment}")
    public String paymentRequestTopic;

    private final MessageProducerService messageProducerService;
    private final PaymentAuthRequestToMessageConverter paymentAuthRequestToMessageConverter;
    private final PaymentPreAuthRequestToMessageConverter paymentPreAuthRequestToMessageConverter;
    private final PaymentPostAuthRequestToMessageConverter paymentPostAuthRequestToMessageConverter;
    private final CreatePaymentResponseMapper createPaymentResponseMapper;

    public PaymentRequestMessagePublisherService(MessageProducerService messageProducerService,
                                                 PaymentAuthRequestToMessageConverter paymentAuthRequestToMessageConverter,
                                                 PaymentPreAuthRequestToMessageConverter paymentPreAuthRequestToMessageConverter,
                                                 PaymentPostAuthRequestToMessageConverter paymentPostAuthRequestToMessageConverter,
                                                 CreatePaymentResponseMapper createPaymentResponseMapper) {
        this.messageProducerService = messageProducerService;
        this.paymentAuthRequestToMessageConverter = paymentAuthRequestToMessageConverter;
        this.paymentPreAuthRequestToMessageConverter = paymentPreAuthRequestToMessageConverter;
        this.paymentPostAuthRequestToMessageConverter = paymentPostAuthRequestToMessageConverter;
        this.createPaymentResponseMapper = createPaymentResponseMapper;
    }

    public CreatePaymentResponse publishAuthPayment(PaymentAuthRequest paymentAuthRequest) {
        logger.info("publishing auth payment with conversationId: {}", paymentAuthRequest.getConversationId());
        PaymentAuthRequestMessage paymentAuthRequestMessage = paymentAuthRequestToMessageConverter.convert(paymentAuthRequest);

        messageProducerService.write(paymentRequestTopic, paymentAuthRequestMessage);

        return createPaymentResponseMapper.prepareResponse(paymentAuthRequestMessage);
    }

    public CreatePaymentResponse publishPreAuthPayment(PaymentPreAuthRequest paymentPreAuthRequest) {
        logger.info("publishing pre-auth payment with conversationId: {}", paymentPreAuthRequest.getConversationId());
        PaymentPreAuthRequestMessage paymentPreAuthRequestMessage = paymentPreAuthRequestToMessageConverter.convert(paymentPreAuthRequest);

        messageProducerService.write(paymentRequestTopic, paymentPreAuthRequestMessage);

        return createPaymentResponseMapper.prepareResponse(paymentPreAuthRequestMessage);
    }

    public CreatePaymentResponse publishPostAuthPayment(@Valid PaymentPostAuthRequest paymentPostAuthRequest) {
        logger.info("publishing post-auth payment with uniqueId: {}", paymentPostAuthRequest.getIyzicoPaymentUniqueIdentifier());
        PaymentPostAuthRequestMessage paymentPostAuthRequestMessage = paymentPostAuthRequestToMessageConverter.convert(paymentPostAuthRequest);

        messageProducerService.write(paymentRequestTopic, paymentPostAuthRequestMessage);

        return createPaymentResponseMapper.prepareResponse(paymentPostAuthRequestMessage);
    }
}
