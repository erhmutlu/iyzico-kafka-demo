package org.erhanmutlu.payment.rest.application.request;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PaymentAuthRequest {

    @NotNull
    private BigDecimal price;

    private String conversationId;

    @NotNull
    private String apiKey;

    @NotNull
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
