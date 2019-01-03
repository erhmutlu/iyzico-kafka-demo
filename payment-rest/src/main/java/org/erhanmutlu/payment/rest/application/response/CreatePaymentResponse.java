package org.erhanmutlu.payment.rest.application.response;

public class CreatePaymentResponse extends Response{

    private String iyzicoPaymentUniqueIdentifier;

    public String getIyzicoPaymentUniqueIdentifier() {
        return iyzicoPaymentUniqueIdentifier;
    }

    public void setIyzicoPaymentUniqueIdentifier(String iyzicoPaymentUniqueIdentifier) {
        this.iyzicoPaymentUniqueIdentifier = iyzicoPaymentUniqueIdentifier;
    }
}
