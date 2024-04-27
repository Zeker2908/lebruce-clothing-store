package ru.lebruce.store.service;

import ru.lebruce.store.model.Order;
import ru.lebruce.store.model.User;

import java.util.List;

public interface OrderService {
    List<Order> findAllOrders();

    Order saveOrder(Order order);

    Order findByUser(User user);

    Order updateOrder(Order order);

    void deleteOrder(Long orderId);

    void deleteOrder(User user);
}
