package org.erhanmutlu.payment.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class PaymentRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentRestApplication.class, args);
    }
}
