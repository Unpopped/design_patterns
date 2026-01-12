package org.example.notification;

import org.example.entity.Order;

public interface Observer {
    void update(Order order, String message);
}
