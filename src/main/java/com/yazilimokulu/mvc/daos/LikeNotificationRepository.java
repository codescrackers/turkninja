package com.yazilimokulu.mvc.daos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yazilimokulu.mvc.entities.LikeNotification;

public interface LikeNotificationRepository extends BaseRepository<LikeNotification, Long> {
	
	@Query("select n from LikeNotification n where n.post.id=:postId")
	LikeNotification findByPostId(@Param("postId") Long postId);
	
	List<LikeNotification> findByChecked(boolean checked);
	
}
