package com.E_com.E_commerce.Repository;

import com.E_com.E_commerce.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT r FROM Rating r WHERE r.product.id=:productId")
    public List<Rating> getAllProductsRating(@Param("productId") Long productId);
}
