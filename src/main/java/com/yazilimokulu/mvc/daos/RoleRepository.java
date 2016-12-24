package com.yazilimokulu.mvc.daos;

import com.yazilimokulu.mvc.entities.Role;

public interface RoleRepository extends BaseRepository<Role, Long> {

    Role findByName(String name);
}
