package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lebruce.store.domain.model.Order;
import ru.lebruce.store.domain.model.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByUser(User user);

    void deleteByOrderId(Long orderId);

    void deleteByUser(User user);
}
