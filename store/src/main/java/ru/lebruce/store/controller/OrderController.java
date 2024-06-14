package ru.lebruce.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.domain.model.Order;
import ru.lebruce.store.domain.model.OrderStatus;
import ru.lebruce.store.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @PostMapping
    public Order createOrder() {
        return orderService.createOrder();
    }

    @PutMapping
    public Order updateOrder(@RequestBody Long orderId, OrderStatus newStatus) {
        return orderService.updateOrderStatus(orderId, newStatus);
    }

}

