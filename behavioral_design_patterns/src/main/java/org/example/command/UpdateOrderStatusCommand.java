package org.example.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Order;
import org.example.entity.OrderStatus;
import org.example.repository.OrderRepository;

@Slf4j
@RequiredArgsConstructor
public class UpdateOrderStatusCommand implements OrderCommand {
    private final Order order;
    private final OrderStatus newStatus;
    private final OrderRepository orderRepository;
    private OrderStatus previousStatus;

    @Override
    public Order execute() {
        log.info("Executing UpdateOrderStatusCommand for order: {} to status: {}", order.getId(), newStatus);
        previousStatus = order.getStatus();
        order.updateStatus(newStatus);
        Order savedOrder = orderRepository.save(order);
        log.info("Order {} status updated from {} to {}", savedOrder.getId(), previousStatus, newStatus);
        return savedOrder;
    }

    @Override
    public void undo() {
        if (previousStatus != null) {
            log.info("Undoing UpdateOrderStatusCommand for order: {}", order.getId());
            order.updateStatus(previousStatus);
            orderRepository.save(order);
            log.info("Order {} status restored to: {}", order.getId(), previousStatus);
        }
    }
}
