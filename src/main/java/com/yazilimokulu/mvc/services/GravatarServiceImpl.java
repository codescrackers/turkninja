package com.yazilimokulu.mvc.services;

import org.springframework.stereotype.Service;

import com.yazilimokulu.utils.Gravatar;
import com.yazilimokulu.utils.GravatarDefaultImage;
import com.yazilimokulu.utils.GravatarRating;

@Service("gravatarService")
public class GravatarServiceImpl implements GravatarService {

	@Override
	public String getAvatarUrl(String email) {
		Gravatar gravatar = new Gravatar();
		gravatar.setSize(50);
		gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
		gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
		String url = gravatar.getUrl(email);
		return url;
	}

}
