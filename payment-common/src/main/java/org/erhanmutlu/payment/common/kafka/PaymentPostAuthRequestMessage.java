package org.erhanmutlu.payment.common.kafka;

import org.erhanmutlu.payment.common.PaymentType;

import java.math.BigDecimal;

public class PaymentPostAuthRequestMessage extends IyzicoIdempotentMessage {
    private BigDecimal price;
    private String apiKey;
    private String secretKey;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
