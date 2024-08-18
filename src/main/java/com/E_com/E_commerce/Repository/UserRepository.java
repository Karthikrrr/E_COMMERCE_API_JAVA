package com.E_com.E_commerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.E_com.E_commerce.model.Users;


@Repository
public interface UserRepository extends JpaRepository<Users, Long>{

    public Users findByEmail(String email);
}
