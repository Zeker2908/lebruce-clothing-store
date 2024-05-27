package ru.lebruce.store.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shopping_carts")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shoppingCartId;

    //todo убрать JsonIgnore и сделать DTO, чтобы выводило только нужную информацию о корзине
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShoppingCartItem> items;

    @Formula("(select sum(p.price * sci.quantity) from products p, shopping_cart_items sci where sci.product_id = p.product_id and sci.shopping_cart_id = shopping_cart_id)")
    private Double totalPrice;

    @Formula("(select count(*) from shopping_cart_items sci where sci.shopping_cart_id = shopping_cart_id)")
    private Integer quantityOfPosition;
}
