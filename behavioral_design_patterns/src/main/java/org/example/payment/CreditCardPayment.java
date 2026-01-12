package org.example.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreditCardPayment implements PaymentStrategy {

    @Override
    public boolean pay(double amount) {
        log.info("Processing credit card payment of ${}", amount);

        if (amount <= 0) {
            log.warn("Invalid payment amount: {}", amount);
            return false;
        }

        // Simulate payment processing
        log.info("Connecting to credit card payment gateway...");
        log.info("Payment of ${} processed successfully via Credit Card", amount);
        return true;
    }

    @Override
    public String getPaymentMethod() {
        return "CREDIT_CARD";
    }
}
