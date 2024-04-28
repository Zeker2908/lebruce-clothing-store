package ru.lebruce.store.controller;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.model.OrderDetail;
import ru.lebruce.store.service.OrderDetailService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order_detail")
@AllArgsConstructor
public class OrderDetailController {
    private final OrderDetailService service;
    @GetMapping
    public List<OrderDetail> findAllOrderDetail() { return service.findAllOrderDetail(); }

    @GetMapping("/{orderDetailName}")
    public OrderDetail findByOrderDetailName(@PathVariable String orderDetailName){return service.findByOrderDetailName(orderDetailName);}

    @GetMapping("/{orderDetailId}")
    public OrderDetail findByOrderDetailById(@PathVariable Long orderDetailId){return service.findByOrderDetailById(orderDetailId);}

    @PostMapping("save_orderDetail")
    public OrderDetail saveOrderDetail(@RequestBody OrderDetail orderDetail) {
        return service.saveOrderDetail(orderDetail);
    }

    @PutMapping("update_orderDetail")
    public OrderDetail updateOrderDetail(@RequestBody OrderDetail orderDetail) {
        return service.updateOrderDetail(orderDetail);
    }

    @DeleteMapping("delete_order_detail_by_id/{orderDetailId}")
    public void deleteOrderDetail(@PathVariable Long orderDetailId) {
        service.deleteOrderDetail(orderDetailId);
    }

    @DeleteMapping("delete_order_detail_by_name/{orderDetailName}")
    public void deleteOrderDetail(@PathVariable String orderDetailName){ service.deleteOrderDetail(orderDetailName); }


}
