package com.yazilimokulu.mvc.services;

import java.util.List;

import com.yazilimokulu.mvc.entities.LikeNotification;
import com.yazilimokulu.mvc.entities.Post;

public interface LikeNotificationService {

	public void save(Post post);
	
	public List<LikeNotification> getAllUnreadNotifiacitons();
	
	
	
}
