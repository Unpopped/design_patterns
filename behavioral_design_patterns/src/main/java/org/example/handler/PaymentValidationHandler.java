package org.example.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentValidationHandler extends OrderValidationHandler {

    @Override
    public boolean validate(Order order) {
        log.info("Validating payment for order: {}", order.getId());

        if (order.getTotalAmount() <= 0) {
            log.warn("Invalid payment amount {} for order: {}", order.getTotalAmount(), order.getId());
            return false;
        }

        if (order.getPaymentMethod() == null || order.getPaymentMethod().isBlank()) {
            log.warn("No payment method specified for order: {}", order.getId());
            return false;
        }

        log.info("Payment validation passed for order: {}", order.getId());
        return super.validate(order);
    }
}
