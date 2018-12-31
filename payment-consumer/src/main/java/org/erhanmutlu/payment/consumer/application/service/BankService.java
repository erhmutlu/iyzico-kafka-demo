package org.erhanmutlu.payment.consumer.application.service;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BankService {

    public void pay(){
        wait4();
    }

    private void wait4() {
        long start = new Date().getTime();
        while (new Date().getTime() - start < 3000L) {}
    }
}
