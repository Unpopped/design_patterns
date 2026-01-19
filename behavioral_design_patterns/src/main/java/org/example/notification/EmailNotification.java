package org.example.notification;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailNotification implements Observer {

    @Override
    public void update(Order order, String message) {
        log.info("Sending EMAIL notification to customer: {}", order.getCustomerName());
        log.info("Email content: Order #{} - {}", order.getId(), message);
        log.debug("Email sent successfully to customer: {}", order.getCustomerName());
    }
}
