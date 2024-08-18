package com.E_com.E_commerce.ServiceImplementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.E_com.E_commerce.Configuration.JwtProvider;
import com.E_com.E_commerce.Exception.UserException;
import com.E_com.E_commerce.Repository.UserRepository;
import com.E_com.E_commerce.Service.UserService;
import com.E_com.E_commerce.model.Users;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public Users findUserById(Long userId) throws UserException {
        Optional<Users> user = userRepository.findById(userId);
        if(user.isPresent()){
            return user.get();
        }
        throw new UserException("User Not Found With Id : " + userId);
    }

    @Override
    public Users findUserProfileByJwt(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromToken(jwt);

        Users user = userRepository.findByEmail(email);

        if(user == null){
            throw new UserException("User Not Found With Email : " + email);
        }
        return user;
    }
}
