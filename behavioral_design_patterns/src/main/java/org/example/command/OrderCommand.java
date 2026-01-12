package org.example.command;

import org.example.entity.Order;

public interface OrderCommand {
    Order execute();
    void undo();
}
