package org.example.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Order;
import org.example.entity.OrderStatus;
import org.example.repository.OrderRepository;

@Slf4j
@RequiredArgsConstructor
public class CancelOrderCommand implements OrderCommand {
    private final Order order;
    private final OrderRepository orderRepository;
    private OrderStatus previousStatus;

    @Override
    public Order execute() {
        log.info("Executing CancelOrderCommand for order: {}", order.getId());
        previousStatus = order.getStatus();
        order.updateStatus(OrderStatus.CANCELLED);
        Order savedOrder = orderRepository.save(order);
        log.info("Order {} cancelled successfully", savedOrder.getId());
        return savedOrder;
    }

    @Override
    public void undo() {
        if (previousStatus != null) {
            log.info("Undoing CancelOrderCommand for order: {}", order.getId());
            order.updateStatus(previousStatus);
            orderRepository.save(order);
            log.info("Order {} status restored to: {}", order.getId(), previousStatus);
        }
    }
}
