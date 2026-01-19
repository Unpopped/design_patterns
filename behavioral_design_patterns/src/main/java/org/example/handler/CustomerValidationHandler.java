package org.example.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomerValidationHandler extends OrderValidationHandler {

    @Override
    public boolean validate(Order order) {
        log.info("Validating customer info for order: {}", order.getId());

        if (order.getCustomerName() == null || order.getCustomerName().isBlank()) {
            log.warn("Customer name is missing for order: {}", order.getId());
            return false;
        }

        log.info("Customer validation passed for order: {}", order.getId());
        return super.validate(order);
    }
}
