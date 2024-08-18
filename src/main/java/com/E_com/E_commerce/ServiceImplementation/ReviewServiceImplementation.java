package com.E_com.E_commerce.ServiceImplementation;

import com.E_com.E_commerce.Exception.ProductException;
import com.E_com.E_commerce.Repository.ProductRepository;
import com.E_com.E_commerce.Repository.ReviewRepository;
import com.E_com.E_commerce.Request.ReviewRequest;
import com.E_com.E_commerce.Service.ProductService;
import com.E_com.E_commerce.Service.ReviewService;
import com.E_com.E_commerce.model.Product;
import com.E_com.E_commerce.model.Review;
import com.E_com.E_commerce.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImplementation implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Review createReview(ReviewRequest request, Users users) throws ProductException {
        Product product = productService.findProductById(request.getProductId());

        Review review = new Review();
        review.setUser(users);
        review.setProduct(product);
        review.setReview(request.getReview());
        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {
        return reviewRepository.getAllProductsReview(productId);
    }
}
