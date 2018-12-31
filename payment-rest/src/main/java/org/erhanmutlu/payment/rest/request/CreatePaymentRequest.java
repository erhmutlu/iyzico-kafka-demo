package org.erhanmutlu.payment.rest.request;

import javax.validation.constraints.NotNull;

public class CreatePaymentRequest {

    @NotNull
    private String price;

    private String conversationId;

    @NotNull
    private String apiKey;

    @NotNull
    private String secretKey;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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
