package org.example.notification;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SMSNotification implements Observer {

    @Override
    public void update(Order order, String message) {
        log.info("Sending SMS notification to customer: {}", order.getCustomerName());
        log.info("SMS content: Order #{} - {}", order.getId(), message);
        log.debug("SMS sent successfully to customer: {}", order.getCustomerName());
    }
}
