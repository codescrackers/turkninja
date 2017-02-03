package com.yazilimokulu.mvc.daos;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yazilimokulu.mvc.entities.Post;

public interface PostRepository extends BaseRepository<Post, Long> {

    Page<Post> findByHiddenFalse(Pageable pageable);

    // without count
    List<Post> findByHiddenIs(boolean hidden, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE :tagCount = (SELECT COUNT(DISTINCT t.id) FROM Post p2 JOIN p2.tags t WHERE LOWER(t.name) in (:tags) and p = p2)")
    Page<Post> findByTags(@Param("tags") Collection<String> tags, @Param("tagCount") Long tagCount, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE :tagCount = (SELECT COUNT(DISTINCT t.id) FROM Post p2 JOIN p2.tags t WHERE p.hidden = false and LOWER(t.name) in (:tags) and p = p2)")
    Page<Post> findByTagsAndNotHidden(@Param("tags") Collection<String> tags, @Param("tagCount") Long tagCount, Pageable pageable);

    @Query("SELECT p FROM Post p JOIN p.postRatings r WHERE p.hidden = false GROUP BY p ORDER BY SUM(r.value) DESC")
    List<Post> findTopPosts(Pageable pageable);
    
    Page<Post> findByUserUsername(String username,Pageable pageable);
}
