package com.yazilimokulu.mvc.entities;

import org.hibernate.validator.constraints.NotBlank;

import java.time.LocalDateTime;

import javax.validation.constraints.Size;

public class PostEditDto {

    private Long Id = null;

    // haven't figured out how to specify messages for Size.List in the messages file
    @Size.List({
            @Size(min = 3, message = "Title too short"),
            @Size(max = 250, message = "Title too long")
    })
    @NotBlank
    private String title;

    @NotBlank
    @Size(min = 50)
    private String text;

    @NotBlank
    private String tags = "";
    
    private User user;
    
    private String shortUrl;
    
    private boolean mainpage;
    
    private String mainpagePhotoUrl;
    
    private LocalDateTime mainphotoVisibleupdateTime;


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public boolean isMainpage() {
		return mainpage;
	}

	public void setMainpage(boolean mainpage) {
		this.mainpage = mainpage;
	}

	public String getMainpagePhotoUrl() {
		return mainpagePhotoUrl;
	}

	public void setMainpagePhotoUrl(String mainpagePhotoUrl) {
		this.mainpagePhotoUrl = mainpagePhotoUrl;
	}

	public LocalDateTime getMainphotoVisibleupdateTime() {
		return mainphotoVisibleupdateTime;
	}

	public void setMainphotoVisibleupdateTime(LocalDateTime mainphotoVisibleupdateTime) {
		this.mainphotoVisibleupdateTime = mainphotoVisibleupdateTime;
	}
	
    
}
