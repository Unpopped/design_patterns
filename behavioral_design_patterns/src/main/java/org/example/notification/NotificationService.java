package org.example.notification;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NotificationService {
    private final List<Observer> observers = new ArrayList<>();

    public NotificationService(List<Observer> observers) {
        this.observers.addAll(observers);
        log.info("NotificationService initialized with {} observers", observers.size());
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
        log.debug("Observer added. Total observers: {}", observers.size());
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
        log.debug("Observer removed. Total observers: {}", observers.size());
    }

    public void notifyObservers(Order order, String message) {
        log.info("Notifying {} observers about order: {}", observers.size(), order.getId());
        for (Observer observer : observers) {
            observer.update(order, message);
        }
    }
}
