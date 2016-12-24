package com.yazilimokulu.mvc.daos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yazilimokulu.mvc.entities.PostRating;

public interface PostRatingRepository extends BaseRepository<PostRating, Long> {

    @Query("SELECT r FROM PostRating r WHERE r.post.id = :postId AND r.user.id = :userId")
    PostRating findUserRating(@Param("postId") Long postId, @Param("userId") Long userId);
}
