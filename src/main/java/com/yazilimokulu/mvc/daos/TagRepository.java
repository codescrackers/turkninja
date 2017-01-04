package com.yazilimokulu.mvc.daos;

import java.util.List;

import com.yazilimokulu.mvc.entities.Tag;

public interface TagRepository extends BaseRepository<Tag, Long> {

    Tag findByNameIgnoreCase(String name);
    List<Tag> findAll();
}
