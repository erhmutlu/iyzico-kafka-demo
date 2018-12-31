package org.erhanmutlu.payment.consumer.application.manager;

import org.erhanmutlu.payment.consumer.application.service.BankService;
import org.springframework.stereotype.Component;

@Component
public class PaymentManager {

    private final BankService bankService;

    public PaymentManager(BankService bankService) {
        this.bankService = bankService;
    }

    public void pay(){

    }
}
