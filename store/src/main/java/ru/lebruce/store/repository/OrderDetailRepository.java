package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lebruce.store.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    OrderDetail findByOrderDetailId(Long orderDetailId);

    OrderDetail findByQuantity(Integer quantity);

    void deleteByOrderDetailId(Long orderDetailId);
}
