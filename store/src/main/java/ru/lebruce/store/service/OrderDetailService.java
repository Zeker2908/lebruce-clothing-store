package ru.lebruce.store.service;

import ru.lebruce.store.model.OrderDetail;

import java.util.List;

public interface OrderDetailService {

    List<OrderDetail> findAllOrderDetail();

    OrderDetail findByOrderDetailById(Long orderDetailId);

    OrderDetail findByOrderDetailByQuantity(Integer quantity);

    OrderDetail saveOrderDetail(OrderDetail orderDetail);

    OrderDetail updateOrderDetail(OrderDetail orderDetail);

    void deleteOrderDetailById(Long orderDetailId);

}
