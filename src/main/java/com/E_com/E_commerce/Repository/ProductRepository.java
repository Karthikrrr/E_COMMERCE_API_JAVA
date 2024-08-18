package com.E_com.E_commerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.E_com.E_commerce.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query(value = "SELECT p FROM Product p " +
            "WHERE (:category IS NULL OR p.category.name = :category) " +
            "AND (:minPrice IS NULL OR :maxPrice IS NULL OR p.discountedPrice BETWEEN :minPrice AND :maxPrice) " +
            "AND (:minDiscount IS NULL OR p.discountPersent >= :minDiscount) " +
            "ORDER BY " +
            "CASE WHEN :sort = 'price_low' THEN p.discountedPrice END ASC, " +
            "CASE WHEN :sort = 'price_high' THEN p.discountedPrice END DESC")
   List<Product> filterProduct(@Param("category") String category,
                          @Param("minPrice") Integer minPrice,
                          @Param("maxPrice") Integer maxPrice,
                          @Param("minDiscount") Integer minDiscount,
                          @Param("sort") String sort);



    @Query(value = "SELECT * FROM Product", nativeQuery = true)
    List<Product> findAllProducts();
}
