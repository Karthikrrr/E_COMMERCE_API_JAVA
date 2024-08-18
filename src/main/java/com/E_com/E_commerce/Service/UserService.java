package com.E_com.E_commerce.Service;

import com.E_com.E_commerce.Exception.UserException;
import com.E_com.E_commerce.model.Users;


public interface UserService {

    public Users findUserById(Long userId) throws UserException;
    public Users findUserProfileByJwt(String jwt) throws UserException;
}
