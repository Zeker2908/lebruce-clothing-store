package ru.lebruce.store.service;

import org.springframework.transaction.annotation.Transactional;
import ru.lebruce.store.domain.model.Order;
import ru.lebruce.store.domain.model.OrderStatus;

import java.util.List;

public interface OrderService {

    List<Order> getOrders();

    @Transactional
    void createOrder();

    @Transactional
    Order updateOrderStatus(Long orderId, OrderStatus newStatus);
}
