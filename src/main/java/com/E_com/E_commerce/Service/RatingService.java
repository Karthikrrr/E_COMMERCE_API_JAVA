package com.E_com.E_commerce.Service;

import com.E_com.E_commerce.Exception.ProductException;
import com.E_com.E_commerce.Request.RatingRequest;
import com.E_com.E_commerce.model.Rating;
import com.E_com.E_commerce.model.Users;

import java.util.List;

public interface RatingService {

     public Rating createRating(RatingRequest request, Users users) throws ProductException;
     public List<Rating> getProductRating(Long productId);
}
