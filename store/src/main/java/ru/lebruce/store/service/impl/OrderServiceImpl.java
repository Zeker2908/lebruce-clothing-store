package ru.lebruce.store.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lebruce.store.domain.model.Order;
import ru.lebruce.store.domain.model.User;
import ru.lebruce.store.repository.OrderRepository;
import ru.lebruce.store.service.OrderService;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;

    @Override
    public List<Order> findAllOrders() {
        return repository.findAll();
    }

    @Override
    public Order saveOrder(Order order) {
        return repository.save(order);
    }

    @Override
    public Order findByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    public Order updateOrder(Order order) {
        return repository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId) {
        repository.deleteByOrderId(orderId);
    }

    @Override
    @Transactional
    public void deleteOrder(User user) {
        repository.deleteByUser(user);
    }
}
