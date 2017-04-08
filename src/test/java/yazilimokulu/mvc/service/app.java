package yazilimokulu.mvc.service;

import com.yazilimokulu.utils.Gravatar;
import com.yazilimokulu.utils.GravatarDefaultImage;
import com.yazilimokulu.utils.GravatarRating;

public class app {
	
	public static void main(String[] args) {
		Gravatar gravatar = new Gravatar();
		gravatar.setSize(50);
		gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
		gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
		String url = gravatar.getUrl("aliturgutbozkurt@gmail.com");
		System.out.println(url);

	}
	
	

}
