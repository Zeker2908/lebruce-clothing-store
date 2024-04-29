package ru.lebruce.store.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lebruce.store.model.OrderDetail;
import ru.lebruce.store.repository.OrderDetailRepository;
import ru.lebruce.store.service.OrderDetailService;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository repository;

    @Override
    public List<OrderDetail> findAllOrderDetail() {
        return repository.findAll();
    }

    @Override
    public OrderDetail findByOrderDetailById(Long orderDetailId) {
        return repository.findByOrderDetailId(orderDetailId);
    }

    @Override
    public OrderDetail findByOrderDetailByQuantity(Integer quantity) {
        return repository.findByQuantity(quantity);
    }

    @Override
    public OrderDetail saveOrderDetail(OrderDetail orderDetail) {
        return repository.save(orderDetail);
    }

    @Override
    public OrderDetail updateOrderDetail(OrderDetail orderDetail) {
        return repository.save(orderDetail);
    }

    @Override
    @Transactional
    public void deleteOrderDetailById(Long orderDetailId) {
        repository.deleteByOrderDetailId(orderDetailId);
    }


}
