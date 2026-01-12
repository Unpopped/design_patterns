package org.example.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InventoryCheckHandler extends OrderValidationHandler {

    private static final int MAX_AVAILABLE_QUANTITY = 100;

    @Override
    public boolean validate(Order order) {
        log.info("Checking inventory for order: {}", order.getId());

        if (order.getQuantity() <= 0) {
            log.warn("Invalid quantity {} for order: {}", order.getQuantity(), order.getId());
            return false;
        }

        if (order.getQuantity() > MAX_AVAILABLE_QUANTITY) {
            log.warn("Insufficient inventory for order: {}. Requested: {}, Available: {}",
                    order.getId(), order.getQuantity(), MAX_AVAILABLE_QUANTITY);
            return false;
        }

        log.info("Inventory check passed for order: {}", order.getId());
        return super.validate(order);
    }
}
