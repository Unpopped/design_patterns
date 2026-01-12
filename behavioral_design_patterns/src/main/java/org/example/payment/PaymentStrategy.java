package org.example.payment;

public interface PaymentStrategy {
    boolean pay(double amount);
    String getPaymentMethod();
}
