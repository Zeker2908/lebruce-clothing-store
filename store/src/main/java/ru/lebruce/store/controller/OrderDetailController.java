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

    @GetMapping("/{orderDetailId}")
    public OrderDetail findByOrderDetailById(@PathVariable Long orderDetailId){return service.findByOrderDetailById(orderDetailId);}

    @GetMapping("/{orderDetailQuantity}")
    public OrderDetail findByOrderDetailByQuantity(@PathVariable Integer orderDetailQuantity){return service.findByOrderDetailByQuantity(orderDetailQuantity);}

    @PostMapping("save_orderDetail")
    public OrderDetail saveOrderDetail(@RequestBody OrderDetail orderDetail) {
        return service.saveOrderDetail(orderDetail);
    }

    @PutMapping("update_orderDetail")
    public OrderDetail updateOrderDetail(@RequestBody OrderDetail orderDetail) {
        return service.updateOrderDetail(orderDetail);
    }

    @DeleteMapping("delete_orderDetail_by_id/{orderDetailId}")
    public void deleteOrderDetail(@PathVariable Long orderDetailId){
        service.deleteOrderDetailById(orderDetailId);
    }

}
