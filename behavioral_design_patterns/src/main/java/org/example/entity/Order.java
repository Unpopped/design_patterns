package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private double totalAmount;
    private String paymentMethod;
    private int quantity;

    public Order(String customerName, double totalAmount, int quantity) {
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.quantity = quantity;
        this.status = OrderStatus.PENDING;
    }

    public void updateStatus(OrderStatus newStatus) {
        this.status = newStatus;
        log.info("Order {} status updated to: {}", id, newStatus);
    }
}
