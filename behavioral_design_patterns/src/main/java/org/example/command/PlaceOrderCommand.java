package org.example.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Order;
import org.example.entity.OrderStatus;
import org.example.repository.OrderRepository;

@Slf4j
@RequiredArgsConstructor
public class PlaceOrderCommand implements OrderCommand {
    private final Order order;
    private final OrderRepository orderRepository;
    private Order savedOrder;

    @Override
    public Order execute() {
        log.info("Executing PlaceOrderCommand for customer: {}", order.getCustomerName());
        order.updateStatus(OrderStatus.PLACED);
        savedOrder = orderRepository.save(order);
        log.info("Order placed successfully with ID: {}", savedOrder.getId());
        return savedOrder;
    }

    @Override
    public void undo() {
        if (savedOrder != null) {
            log.info("Undoing PlaceOrderCommand for order: {}", savedOrder.getId());
            savedOrder.updateStatus(OrderStatus.CANCELLED);
            orderRepository.save(savedOrder);
            log.info("Order {} has been cancelled", savedOrder.getId());
        }
    }
}
