package org.erhanmutlu.payment.common.kafka;

public abstract class IyzicoIdempotentMessage {

    private String uniqueId;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
