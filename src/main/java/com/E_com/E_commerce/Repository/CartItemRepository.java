package com.E_com.E_commerce.Repository;

import com.E_com.E_commerce.model.Cart;
import com.E_com.E_commerce.model.CartItem;
import com.E_com.E_commerce.model.Product;
import jakarta.persistence.Embeddable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query(value =  "SELECT c FROM CartItem c WHERE c.cart = :cart AND c.product = :product AND c.size = :size AND c.userId = :userId", nativeQuery = false)
    CartItem isCartItemExists(@Param("cart") Cart cart, @Param("product") Product  product, @Param("size") String size, @Param("userId") Long userId);
}
