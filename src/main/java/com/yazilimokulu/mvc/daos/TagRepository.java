package com.yazilimokulu.mvc.daos;

import com.yazilimokulu.mvc.entities.Tag;

public interface TagRepository extends BaseRepository<Tag, Long> {

    Tag findByNameIgnoreCase(String name);
}
