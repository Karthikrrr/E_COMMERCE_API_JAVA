package com.E_com.E_commerce.Service;

import com.E_com.E_commerce.Exception.ProductException;
import com.E_com.E_commerce.Request.ReviewRequest;
import com.E_com.E_commerce.model.Review;
import com.E_com.E_commerce.model.Users;

import java.util.List;

public interface ReviewService {

    public Review createReview(ReviewRequest request, Users users) throws ProductException;
    public List<Review> getAllReview(Long productId);
}
