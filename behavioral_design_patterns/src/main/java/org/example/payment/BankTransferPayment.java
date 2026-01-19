package org.example.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BankTransferPayment implements PaymentStrategy {

    @Override
    public boolean pay(double amount) {
        log.info("Processing bank transfer payment of ${}", amount);

        if (amount <= 0) {
            log.warn("Invalid payment amount: {}", amount);
            return false;
        }

        // Simulate bank transfer processing
        log.info("Initiating bank transfer...");
        log.info("Payment of ${} processed successfully via Bank Transfer", amount);
        return true;
    }

    @Override
    public String getPaymentMethod() {
        return "BANK_TRANSFER";
    }
}
