package com.yazilimokulu.mvc.daos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yazilimokulu.mvc.entities.CommentRating;

public interface CommentRatingRepository extends BaseRepository<CommentRating, Long> {

    @Query("SELECT r FROM CommentRating r WHERE r.comment.id = :commentId AND r.user.id = :userId")
    CommentRating findUserRating(@Param("commentId") Long commentId, @Param("userId") Long userId);
}
