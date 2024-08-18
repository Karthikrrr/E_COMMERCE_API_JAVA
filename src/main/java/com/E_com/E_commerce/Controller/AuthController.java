package com.E_com.E_commerce.Controller;

import com.E_com.E_commerce.Service.CartService;
import com.E_com.E_commerce.ServiceImplementation.CartItemServiceImplementation;
import com.E_com.E_commerce.ServiceImplementation.CartServiceImplementation;
import com.E_com.E_commerce.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.E_com.E_commerce.Configuration.JwtProvider;
import com.E_com.E_commerce.Exception.UserException;
import com.E_com.E_commerce.Repository.UserRepository;
import com.E_com.E_commerce.Request.LoginRequest;
import com.E_com.E_commerce.Responce.AuthResponce;
import com.E_com.E_commerce.ServiceImplementation.CustomUserServiceImplementation;
import com.E_com.E_commerce.model.Users;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private   JwtProvider jwtProvider;

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private  CustomUserServiceImplementation customUserServiceImplementation;

    @Autowired
    private CartServiceImplementation cartService;



    @PostMapping("/signup")
    public ResponseEntity<AuthResponce> createUserHandler(@RequestBody Users user) throws UserException{

        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        Users isEmailExist = userRepository.findByEmail(email);

        if(isEmailExist != null){
            throw new UserException("Email is Already User With Another Account");
        }

        Users createdUser = new Users();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);
        
        Users saveUser = userRepository.save(createdUser);

        Cart cart = cartService.createCart(saveUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(saveUser.getEmail(), saveUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);

        AuthResponce authResponce = new AuthResponce();
        authResponce.setJwt(token);
        authResponce.setMessage("SignUp Success!!");


        return new ResponseEntity<>(authResponce, HttpStatus.CREATED);

    }
    
    @PostMapping("/signin")
    public ResponseEntity<AuthResponce> createUserHandler(@RequestBody LoginRequest loginRequest){
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);

        AuthResponce authResponce = new AuthResponce();
        authResponce.setJwt(token);
        authResponce.setMessage("SignIn Success!!");

        return new ResponseEntity<>(authResponce, HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserServiceImplementation.loadUserByUsername(username);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid Username");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}



