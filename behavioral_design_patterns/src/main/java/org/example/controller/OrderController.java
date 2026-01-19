package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.OrderRequest;
import org.example.dto.OrderResponse;
import org.example.entity.OrderStatus;
import org.example.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest request) {
        log.info("Received request to place order for customer: {}", request.getCustomerName());
        OrderResponse response = orderService.placeOrder(request);
        HttpStatus status = OrderStatus.PLACED == response.getStatus() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        log.info("Received request to get order: {}", id);
        OrderResponse response = orderService.getOrder(id);
        if (response.getId() == null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        log.info("Received request to get all orders");
        List<OrderResponse> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderResponse>> getOrdersByStatus(@PathVariable OrderStatus status) {
        log.info("Received request to get orders with status: {}", status);
        List<OrderResponse> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long id) {
        log.info("Received request to cancel order: {}", id);
        OrderResponse response = orderService.cancelOrder(id);
        if (response.getId() == null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {
        log.info("Received request to update order {} status to: {}", id, status);
        OrderResponse response = orderService.updateOrderStatus(id, status);
        if (response.getId() == null) {
            return new ResponseEntity<>(response, HttpStatus.I_AM_A_TEAPOT);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/undo")
    public ResponseEntity<OrderResponse> undoLastCommand() {
        log.info("Received request to undo last command");
        OrderResponse response = orderService.undoLastCommand();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/payment-methods")
    public ResponseEntity<List<String>> getAvailablePaymentMethods() {
        log.info("Received request to get available payment methods");
        List<String> paymentMethods = orderService.getAvailablePaymentMethods();
        return ResponseEntity.ok(paymentMethods);
    }
}
