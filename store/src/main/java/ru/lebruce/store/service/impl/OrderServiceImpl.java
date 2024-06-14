package ru.lebruce.store.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lebruce.store.domain.model.*;
import ru.lebruce.store.exception.AddressNotFoundException;
import ru.lebruce.store.exception.OrderNotFoundException;
import ru.lebruce.store.repository.OrderItemRepository;
import ru.lebruce.store.repository.OrderRepository;
import ru.lebruce.store.service.OrderService;
import ru.lebruce.store.service.ProductSizeService;
import ru.lebruce.store.service.ShoppingCartService;
import ru.lebruce.store.service.UserService;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private UserService userService;
    private ShoppingCartService shoppingCartService;
    private ProductSizeService productSizeService;

    @Override
    public List<Order> getOrders() {
        User user = userService.getCurrentUser();
        return orderRepository.findByUser(user);
    }


    @Override
    @Transactional
    public void createOrder() {
        User user = userService.getCurrentUser();
        ShoppingCart cart = shoppingCartService.getCurrentShoppingCart();
        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Корзина пустая");
        }
        if (user.getAddresses().isEmpty()) {
            throw new AddressNotFoundException("Пустой адрес");
        }

        Order order = Order.builder()
                .user(user)
                .totalPrice(cart.getTotalPrice())
                .build();

        order = orderRepository.save(order);

        for (ShoppingCartItem cartItem : cart.getItems()) {
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(cartItem.getProduct())
                    .size(cartItem.getSize())
                    .quantity(cartItem.getQuantity())
                    .priceForOne(cartItem.getPriceForOne())
                    .totalPrice(cartItem.getTotalPrice())
                    .build();
            orderItemRepository.save(orderItem);

            productSizeService.updateQuantity(cartItem.getSize().getId(), -cartItem.getQuantity());
        }
        shoppingCartService.cleanShoppingCart();

    }

    @Override
    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Заказ не найден по айди " + orderId));
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }
}
