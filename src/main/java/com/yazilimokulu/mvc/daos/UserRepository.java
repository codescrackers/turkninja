package com.yazilimokulu.mvc.daos;

import com.yazilimokulu.mvc.entities.User;

public interface UserRepository extends BaseRepository<User, Long> {

    User findByUsernameIgnoreCase(String username);

    User findByEmailIgnoreCase(String email);

    User findByUsernameOrEmail(String username, String email);
}
