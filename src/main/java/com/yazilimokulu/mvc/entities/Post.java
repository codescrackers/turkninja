package com.yazilimokulu.mvc.entities;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.util.StringUtils;

import com.yazilimokulu.mvc.converters.MarkdownConverter;
import com.yazilimokulu.utils.LocalDateTimePersistenceConverter;
import com.yazilimokulu.utils.StringUtil;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(length = 250, nullable = false)
    private String title;

    @Lob
    private String shortTextPart;

    @Lob
    @Column(nullable = false)
    private String fullPostText;

    @Column(nullable = false)
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private boolean hidden = false;
    
    @Column(nullable = false)
    private boolean mainpage = false;
    
    @Column
    private String mainpagePhotoUrl;
    
    @Column(nullable = true)
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    private LocalDateTime mainphotoVisibleupdateTime=LocalDateTime.now();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "posts_tags",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    @OrderBy("name ASC")
    private Collection<Tag> tags = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    @org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
    @OrderBy("dateTime ASC")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<PostRating> postRatings = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="LIKE_NOTFY_ID")
    private LikeNotification likeNotification;
    
    @Transient
    private String dateStr;
    
    @Transient
    private String shortUrl;

    public static String shortPartSeparator() {
        return "===cut===";
    }

    public boolean hasShortTextPart() {
        return !StringUtils.isEmpty(shortTextPart);
    }

    public String shortTextPartHtml() {
        return MarkdownConverter.toHtml(getShortTextPart());
    }

    public String fullPostTextHtml() {
        return MarkdownConverter.toHtml(getFullPostText().replace(shortPartSeparator(), ""));
    }

    public List<Comment> topLevelComments() {
        return comments.stream().filter(c -> c.getParentComment() == null).collect(Collectors.toList());
    }

    public int getRatingSum() {
        return postRatings.stream().mapToInt(Rating::getValue).sum();
    }

    public short getUserVoteValue(Long userId) {
        if (userId == null)
            return 0;

        Optional<PostRating> rating = postRatings.stream().filter(r -> r.getUser().getId().equals(userId)).findFirst();
        return rating.isPresent() ? rating.get().getValue() : 0;
    }

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

    public String getShortTextPart() {
        return shortTextPart;
    }

    public void setShortTextPart(String shortTextPart) {
        this.shortTextPart = shortTextPart;
    }

    public String getFullPostText() {
        return fullPostText;
    }

    public void setFullPostText(String fullPostText) {
        this.fullPostText = fullPostText;
    }

    public Collection<Tag> getTags() {
        return tags;
    }

    public void setTags(Collection<Tag> tags) {
        this.tags = tags;
    }

    public LocalDateTime getDateTime() {
    	return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public List<PostRating> getPostRatings() {
        return postRatings;
    }

    public void setPostRatings(List<PostRating> postRatings) {
        this.postRatings = postRatings;
    }

	public String getDateStr() {
		return getDateTime().getDayOfMonth()+" "+getDateTime().getMonth().getDisplayName(TextStyle.FULL, new Locale("tr", "TR")) +" "+getDateTime().getYear();
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getShortUrl() {
		return StringUtil.clearTurkishChars(this.title).toLowerCase().replace(" ", "-");
	}

	public LikeNotification getLikeNotification() {
		return likeNotification;
	}

	public void setLikeNotification(LikeNotification likeNotification) {
		this.likeNotification = likeNotification;
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
