package com.E_com.E_commerce.ServiceImplementation;

import com.E_com.E_commerce.Exception.ProductException;
import com.E_com.E_commerce.Repository.RatingRepository;
import com.E_com.E_commerce.Request.RatingRequest;
import com.E_com.E_commerce.Service.ProductService;
import com.E_com.E_commerce.Service.RatingService;
import com.E_com.E_commerce.model.Product;
import com.E_com.E_commerce.model.Rating;
import com.E_com.E_commerce.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class RatingServiceImplementation implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ProductService productService;

    @Override
    public Rating createRating(RatingRequest request, Users users) throws ProductException {
        Product product = productService.findProductById(request.getProductId());
        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setUser(users);
        rating.setRating(request.getRating());
        rating.setCreatedAt(LocalDateTime.now());
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductRating(Long productId) {
        return ratingRepository.getAllProductsRating(productId);
    }
}
