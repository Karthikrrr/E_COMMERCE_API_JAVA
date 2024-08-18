package com.E_com.E_commerce.Controller;

import com.E_com.E_commerce.Exception.ProductException;
import com.E_com.E_commerce.Exception.UserException;
import com.E_com.E_commerce.Request.RatingRequest;
import com.E_com.E_commerce.Service.RatingService;
import com.E_com.E_commerce.Service.UserService;
import com.E_com.E_commerce.model.Rating;
import com.E_com.E_commerce.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private UserService userService;

    @Autowired
    private RatingService ratingService;


    @PostMapping("/create")
    public ResponseEntity<Rating> createRating(@RequestBody RatingRequest request, @RequestHeader("Authorization") String jwt) throws UserException, ProductException{

        Users user = userService.findUserProfileByJwt(jwt);

        Rating rating = ratingService.createRating(request, user);

        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getProductsRating(@PathVariable Long productId, @RequestHeader("Authorization") String jwt) throws UserException, ProductException{

        Users user = userService.findUserProfileByJwt(jwt);
        List<Rating> ratings = ratingService.getProductRating(productId);

        return new ResponseEntity<>(ratings, HttpStatus.CREATED);
    }
}
