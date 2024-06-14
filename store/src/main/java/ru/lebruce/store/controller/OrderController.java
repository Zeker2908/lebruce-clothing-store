package ru.lebruce.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createOrder() {
        orderService.createOrder();
        return ResponseEntity.ok("Ваш заказ успешно создан");
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderStatus status) {
        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok("Ваш заказ успешно изменил статус на " + status.name());
    }

}

