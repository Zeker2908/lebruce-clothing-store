package ru.lebruce.store.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.domain.model.Order;
import ru.lebruce.store.domain.model.User;
import ru.lebruce.store.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService service;

    @GetMapping
    public List<Order> findAllOrders() {
        return service.findAllOrders();
    }

    @GetMapping("/{user}")
    public Order findByUser(@PathVariable User user) {
        return service.findByUser(user);
    }

    @PostMapping
    public Order saveOrder(@RequestBody Order order) {
        return service.saveOrder(order);
    }

    @PutMapping
    public Order updateOrder(@RequestBody Order order) {
        return service.updateOrder(order);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        service.deleteOrder(orderId);
    }

    @DeleteMapping("user/{user}")
    public void deleteOrder(@PathVariable User user) {
        service.deleteOrder(user);
    }
}

