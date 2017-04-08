package com.yazilimokulu.mvc.daos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.yazilimokulu.mvc.entities.Tag;
import com.yazilimokulu.mvc.entities.TagWithPostCount;

public interface TagRepository extends BaseRepository<Tag, Long> {

    Tag findByNameIgnoreCase(String name);
    List<Tag> findAll();
    
    @Query(
            value = "select new com.yazilimokulu.mvc.entities.TagWithPostCount(count(post.id) as postCount,tag) from Tag tag join tag.posts post group by tag order by postCount desc",
            countQuery = "select count(distinct tag) from Tag tag join tag.posts post "
        )
        Page<TagWithPostCount> findAllWithPostCount(Pageable pageable);
}
