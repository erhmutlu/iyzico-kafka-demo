package org.erhanmutlu.payment.common.kafka;

import java.math.BigDecimal;

public class PaymentAuthRequestMessage extends IyzicoIdempotentMessage {
    private BigDecimal price;
    private String conversationId;
    private String apiKey;
    private String secretKey;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
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
