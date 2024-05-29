package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lebruce.store.domain.model.Product;
import ru.lebruce.store.domain.model.ShoppingCart;
import ru.lebruce.store.domain.model.ShoppingCartItem;

@Repository
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {
    void deleteByShoppingCartItemId(Long shoppingCartItemId);

    boolean existsByProductAndShoppingCart(Product product, ShoppingCart shoppingCart);
    
}
