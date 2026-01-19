package org.example.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PayPalPayment implements PaymentStrategy {

    @Override
    public boolean pay(double amount) {
        log.info("Processing PayPal payment of ${}", amount);

        if (amount <= 0) {
            log.warn("Invalid payment amount: {}", amount);
            return false;
        }

        // Simulate PayPal payment processing
        log.info("Connecting to PayPal API...");
        log.info("Payment of ${} processed successfully via PayPal", amount);
        return true;
    }

    @Override
    public String getPaymentMethod() {
        return "PAYPAL";
    }
}
