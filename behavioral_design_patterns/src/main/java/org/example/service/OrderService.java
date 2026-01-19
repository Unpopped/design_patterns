package org.example.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.command.CancelOrderCommand;
import org.example.command.OrderCommand;
import org.example.command.PlaceOrderCommand;
import org.example.command.UpdateOrderStatusCommand;
import org.example.dto.OrderRequest;
import org.example.dto.OrderResponse;
import org.example.entity.Order;
import org.example.entity.OrderStatus;
import org.example.handler.CustomerValidationHandler;
import org.example.handler.InventoryCheckHandler;
import org.example.handler.OrderValidationHandler;
import org.example.handler.PaymentValidationHandler;
import org.example.notification.NotificationService;
import org.example.payment.PaymentStrategy;
import org.example.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final NotificationService notificationService;
    private final CustomerValidationHandler customerValidationHandler;
    private final InventoryCheckHandler inventoryCheckHandler;
    private final PaymentValidationHandler paymentValidationHandler;
    private final List<PaymentStrategy> paymentStrategies;

    private Map<String, PaymentStrategy> paymentStrategyMap;
    private OrderValidationHandler validationChain;
    private final Stack<OrderCommand> commandHistory = new Stack<>();

    @PostConstruct
    public void init() {
        paymentStrategyMap = paymentStrategies.stream()
                .collect(Collectors.toMap(PaymentStrategy::getPaymentMethod, Function.identity()));
        log.info("Initialized {} payment strategies: {}", paymentStrategyMap.size(), paymentStrategyMap.keySet());

        customerValidationHandler.setNext(inventoryCheckHandler);
        inventoryCheckHandler.setNext(paymentValidationHandler);
        validationChain = customerValidationHandler;
        log.info("Validation chain initialized: Customer -> Inventory -> Payment");
    }

    public OrderResponse placeOrder(OrderRequest request) {
        log.info("Placing order for customer: {}", request.getCustomerName());

        Order order = Order.builder()
                .customerName(request.getCustomerName())
                .totalAmount(request.getTotalAmount())
                .quantity(request.getQuantity())
                .paymentMethod(request.getPaymentMethod())
                .status(OrderStatus.PENDING)
                .build();

        Order savedOrder = orderRepository.save(order);

        if (!validationChain.validate(savedOrder)) {
            log.warn("Order validation failed for order: {}", savedOrder.getId());
            savedOrder.setStatus(OrderStatus.VALIDATION_FAILED);
            orderRepository.save(savedOrder);
            notificationService.notifyObservers(savedOrder, "Order validation failed");
            return buildResponse(savedOrder, "Order validation failed");
        }

        PaymentStrategy paymentStrategy = paymentStrategyMap.get(request.getPaymentMethod());
        if (paymentStrategy == null) {
            log.warn("Unknown payment method: {}", request.getPaymentMethod());
            savedOrder.setStatus(OrderStatus.PAYMENT_METHOD_INVALID);
            orderRepository.save(savedOrder);
            return buildResponse(savedOrder, "Invalid payment method: " + request.getPaymentMethod());
        }

        if (!paymentStrategy.pay(request.getTotalAmount())) {
            log.warn("Payment failed for order: {}", savedOrder.getId());
            savedOrder.setStatus(OrderStatus.PAYMENT_FAILED);
            orderRepository.save(savedOrder);
            notificationService.notifyObservers(savedOrder, "Payment failed");
            return buildResponse(savedOrder, "Payment processing failed");
        }

        OrderCommand placeOrderCommand = new PlaceOrderCommand(savedOrder, orderRepository);
        Order placedOrder = placeOrderCommand.execute();
        commandHistory.push(placeOrderCommand);

        notificationService.notifyObservers(placedOrder, "Your order has been placed successfully!");

        return buildResponse(placedOrder, "Order placed successfully");
    }

    public OrderResponse cancelOrder(Long orderId) {
        log.info("Cancelling order: {}", orderId);

        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            log.warn("Order not found: {}", orderId);
            return OrderResponse.builder()
                    .message("Order not found")
                    .build();
        }

        Order order = orderOpt.get();
        if (OrderStatus.CANCELLED == order.getStatus()) {
            log.warn("Order {} is already cancelled", orderId);
            return buildResponse(order, "Order is already cancelled");
        }

        OrderCommand cancelCommand = new CancelOrderCommand(order, orderRepository);
        Order cancelledOrder = cancelCommand.execute();
        commandHistory.push(cancelCommand);

        notificationService.notifyObservers(cancelledOrder, "Your order has been cancelled");

        return buildResponse(cancelledOrder, "Order cancelled successfully");
    }

    public OrderResponse updateOrderStatus(Long orderId, OrderStatus newStatus) {
        log.info("Updating order {} status to: {}", orderId, newStatus);

        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            log.warn("Order not found: {}", orderId);
            return OrderResponse.builder()
                    .message("Order not found")
                    .build();
        }

        OrderCommand updateCommand = new UpdateOrderStatusCommand(orderOpt.get(), newStatus, orderRepository);
        Order updatedOrder = updateCommand.execute();
        commandHistory.push(updateCommand);

        notificationService.notifyObservers(updatedOrder, "Order status updated to: " + newStatus);

        return buildResponse(updatedOrder, "Order status updated to: " + newStatus);
    }

    public OrderResponse undoLastCommand() {
        if (commandHistory.isEmpty()) {
            log.warn("No commands to undo");
            return OrderResponse.builder()
                    .message("No commands to undo")
                    .build();
        }

        OrderCommand lastCommand = commandHistory.pop();
        lastCommand.undo();
        log.info("Last command undone successfully");

        return OrderResponse.builder()
                .message("Last command undone successfully")
                .build();
    }

    public OrderResponse getOrder(Long orderId) {
        log.debug("Fetching order: {}", orderId);

        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            return OrderResponse.builder()
                    .message("Order not found")
                    .build();
        }

        return buildResponse(orderOpt.get(), "Order retrieved successfully");
    }

    public List<OrderResponse> getAllOrders() {
        log.debug("Fetching all orders");
        return orderRepository.findAll().stream()
                .map(order -> buildResponse(order, null))
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getOrdersByStatus(OrderStatus status) {
        log.debug("Fetching orders with status: {}", status);
        return orderRepository.findByStatus(status).stream()
                .map(order -> buildResponse(order, null))
                .collect(Collectors.toList());
    }

    public List<String> getAvailablePaymentMethods() {
        log.debug("Fetching available payment methods");
        return paymentStrategyMap.keySet().stream().sorted().collect(Collectors.toList());
    }

    private OrderResponse buildResponse(Order order, String message) {
        return OrderResponse.builder()
                .id(order.getId())
                .customerName(order.getCustomerName())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .quantity(order.getQuantity())
                .paymentMethod(order.getPaymentMethod())
                .message(message)
                .build();
    }
}
