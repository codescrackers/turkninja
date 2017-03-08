package com.yazilimokulu.mvc.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CollectionId;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yazilimokulu.mvc.converters.MarkdownConverter;
import com.yazilimokulu.utils.LocalDateTimePersistenceConverter;

@Entity
@Table(name = "users")
public class User implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5189291412284790737L;

	public interface CreateValidationGroup {}
    public interface ChangeEmailValidationGroup {}
    public interface ChangePasswordValidationGroup {}
    public interface ProfileInfoValidationGroup {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long Id;

    @Column(unique = true, nullable = false, length = 50)
    // haven't figured out how to specify messages for Size.List in the messages file
    @Size.List({
            @Size(min = 3, message = "Username too short", groups = {CreateValidationGroup.class}),
            @Size(max = 25, message = "Username too long", groups = {CreateValidationGroup.class})
    })
    @NotBlank(groups = {CreateValidationGroup.class})
    @Pattern(regexp = "^[\\p{L}0-9\\._\\- ]+$", groups = {CreateValidationGroup.class})
    private String username;

    @Column(unique = true, nullable = false, length = 50)
    @Email(groups = {CreateValidationGroup.class, ChangeEmailValidationGroup.class})
    @NotBlank(groups = {CreateValidationGroup.class, ChangeEmailValidationGroup.class})
    private String email;

    @Column(nullable = false, length = 80)
    // haven't figured out how to specify messages for Size.List in the messages file
    @Size.List({
            @Size(min = 6, message = "Password too short", groups = {CreateValidationGroup.class, ChangePasswordValidationGroup.class}),
            @Size(max = 80, message = "Password too long", groups = {CreateValidationGroup.class, ChangePasswordValidationGroup.class})
    })
    @NotBlank(groups = {CreateValidationGroup.class, ChangePasswordValidationGroup.class})
    private String password;

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    private LocalDateTime registrationDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(name = "users_followings",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "following_id", referencedColumnName = "id"))
    private List<User> following = null;
    
    @ManyToMany
    @JoinTable(name = "users_followers",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id"))
    private List<User> followers = null;


    @Column(nullable = true, length = 1000)
    @Size(max = 1000, groups = {ProfileInfoValidationGroup.class})
    private String aboutText;

    @Column(nullable = true, length = 80)
    @Pattern(regexp = "^\\s*(https?:\\/\\/.+)?", groups = {ProfileInfoValidationGroup.class})
    @Size(max = 80, groups = {ProfileInfoValidationGroup.class})
    private String websiteLink;

    @Column(nullable = true)
    private String smallAvatarLink;

    @Column(nullable = true)
    private String bigAvatarLink;
    
    @OneToMany(mappedBy = "user")
    private List<Post> posts;
    
    private String gravatarUrl;
    
    private String title;
    
    @Transient
    private String dateStr;
    
    @Transient
    private int followingCount;
    
    @Transient
    private int followersCount;
    
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getAboutText() {
        return aboutText;
    }

    public String getAboutTextHtml() {
        return StringUtils.isEmpty(aboutText) ? "" : MarkdownConverter.toHtml(aboutText);
    }

    public void setAboutText(String aboutText) {
        this.aboutText = aboutText;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public String getWebsiteLinkTitle() {
        return StringUtils.isEmpty(websiteLink) ? "" :
                StringUtils.trimTrailingCharacter(websiteLink.replace("https://", "").replace("http://", "").replace("www.", ""), '/');
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public String getSmallAvatarLink() {
        return smallAvatarLink;
    }

    public void setSmallAvatarLink(String smallAvatarLink) {
        this.smallAvatarLink = smallAvatarLink;
    }

    public String getBigAvatarLink() {
        return bigAvatarLink;
    }

    public void setBigAvatarLink(String bigAvatarLink) {
        this.bigAvatarLink = bigAvatarLink;
    }
    
    public String getDateStr() {
		return getRegistrationDate().getDayOfMonth()+" "+getRegistrationDate().getMonth().getDisplayName(TextStyle.FULL, new Locale("tr", "TR")) +" "+getRegistrationDate().getYear();
	}
    
	public List<User> getFollowing() {
		return following;
	}

	public void setFollowing(List<User> following) {
		this.following = following;
	}

	public List<User> getFollowers() {
		return followers;
	}

	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}
	
	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public boolean hasRole(String role) {
        role = role.toUpperCase();

        if (!role.startsWith("ROLE_"))
            role = "ROLE_" + role;

        final String finalRole = role;
        return getRoles().stream().anyMatch(r -> r.getName().equals(finalRole));
    }

	
    public String getGravatarUrl() {
		return gravatarUrl;
	}

	public void setGravatarUrl(String gravatarUrl) {
		this.gravatarUrl = gravatarUrl;
	}
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	

	public int getFollowingCount() {
		return this.following.size();
	}


	public int getFollowersCount() {
		return this.followers.size();
	}


	@Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roles +
                '}';
    }
}
