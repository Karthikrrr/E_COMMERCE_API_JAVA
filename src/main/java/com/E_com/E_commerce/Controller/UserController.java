package com.E_com.E_commerce.Controller;


import com.E_com.E_commerce.Exception.UserException;
import com.E_com.E_commerce.Service.UserService;
import com.E_com.E_commerce.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<Users> getUserProfile(@RequestHeader("Authorization") String jwt) throws UserException{

        Users users = userService.findUserProfileByJwt(jwt);

        return new ResponseEntity<>(users, HttpStatus.ACCEPTED);
    }
}
