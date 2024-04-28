package ru.lebruce.store.service;

import ru.lebruce.store.model.OrderDetail;

import java.util.List;

public interface OrderDetailService {

    List<OrderDetail> findAllOrderDetail();

    OrderDetail findByOrderDetailName(String OrderDetailName);
    OrderDetail findByOrderDetailById(Long orderDetailId);

    OrderDetail saveOrderDetail(OrderDetail orderDetail);

    OrderDetail updateOrderDetail(OrderDetail orderDetail);

    void deleteOrderDetail(Long orderDetailById);

    void deleteOrderDetail(String orderDetailName);}
