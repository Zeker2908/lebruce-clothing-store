package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lebruce.store.model.Order;
import ru.lebruce.store.model.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByUser(User user);

    void deleteByOrderId(Long orderId);

    void deleteByUser(User user);
}
