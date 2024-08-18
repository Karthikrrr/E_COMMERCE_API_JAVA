package com.E_com.E_commerce.Service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.E_com.E_commerce.Exception.ProductException;
import com.E_com.E_commerce.Request.CreateProductRequest;
import com.E_com.E_commerce.model.Product;

public interface ProductService {
    Product createProduct(CreateProductRequest request);

    List<Product> findAllProducts();

    String deletProduct(Long productId) throws ProductException;

    Product updateProduct(Long productId, Product product) throws ProductException;

    Product findProductById(Long id) throws ProductException;

    List<Product> findProductByCategory(String category);

    Page<Product> getAllProduct(String category, List<String> colors, List<String> size, Integer minPrice,
            Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize);

}
