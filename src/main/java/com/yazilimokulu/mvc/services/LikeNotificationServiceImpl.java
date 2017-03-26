package com.yazilimokulu.mvc.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yazilimokulu.mvc.daos.LikeNotificationRepository;
import com.yazilimokulu.mvc.daos.PostRepository;
import com.yazilimokulu.mvc.entities.LikeNotification;
import com.yazilimokulu.mvc.entities.Post;
import com.yazilimokulu.mvc.entities.User;

@Service
public class LikeNotificationServiceImpl implements LikeNotificationService {

	@Autowired
	LikeNotificationRepository likeNotificationRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserService userService;
	
	@Override
	public void save(Post post) {
		LikeNotification likeNotification;
		List<User> creatorList;
		if(likeNotificationRepository.findByPostId(post.getId()) !=null){
			likeNotification=likeNotificationRepository.findByPostId(post.getId());
			creatorList=likeNotification.getCreatorUsers();
		}else{
			likeNotification = new LikeNotification();
			creatorList = new ArrayList<>();
		}
		User currentUser=userService.currentUser();
		creatorList.add(currentUser);
		currentUser.getCreatedNotificaitons().add(likeNotification);
		likeNotification.setCreatorUsers(creatorList);
		likeNotification.setPost(post);
		likeNotification.setUser(post.getUser());
		likeNotification.setChecked(false);
		post.getUser().getNotifications().add(likeNotification);
		post.setLikeNotification(likeNotification);
		likeNotificationRepository.save(likeNotification);
		
	}

	@Override
	public List<LikeNotification> getAllUnCheckedNotifiacitons() {
		return likeNotificationRepository.findByChecked(false);
	}

	
	
}
