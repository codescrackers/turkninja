package com.yazilimokulu.mvc.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yazilimokulu.mvc.entities.User;

public interface UserRepository extends BaseRepository<User, Long> {

    User findByUsernameIgnoreCase(String username);

    User findByEmailIgnoreCase(String email);

    User findByUsernameOrEmail(String username, String email);
    
    Page<User> findAllByOrderByRegistrationDateDesc(Pageable pageable);

}
