package com.E_com.E_commerce.Controller;


import com.E_com.E_commerce.Exception.ProductException;
import com.E_com.E_commerce.Exception.UserException;
import com.E_com.E_commerce.Request.ReviewRequest;
import com.E_com.E_commerce.Service.ReviewService;
import com.E_com.E_commerce.Service.UserService;
import com.E_com.E_commerce.model.Review;
import com.E_com.E_commerce.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.PrinterException;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest request, @RequestHeader("Authorization") String jwt) throws UserException, ProductException{
        Users user = userService.findUserProfileByJwt(jwt);
        Review review =reviewService.createReview(request, user);

        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getProductsReview(@PathVariable Long productId) throws UserException, ProductException{
        List<Review> reviews = reviewService.getAllReview(productId);

        return new ResponseEntity<>(reviews, HttpStatus.ACCEPTED);
    }
}
