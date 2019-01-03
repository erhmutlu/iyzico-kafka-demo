package org.erhanmutlu.payment.rest.application.request;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PaymentPostAuthRequest {

    @NotNull
    private BigDecimal price;

    @NotNull
    private String iyzicoPaymentUniqueIdentifier;

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

    public String getIyzicoPaymentUniqueIdentifier() {
        return iyzicoPaymentUniqueIdentifier;
    }

    public void setIyzicoPaymentUniqueIdentifier(String iyzicoPaymentUniqueIdentifier) {
        this.iyzicoPaymentUniqueIdentifier = iyzicoPaymentUniqueIdentifier;
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
