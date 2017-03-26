package com.yazilimokulu.mvc.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Notification {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long Id;
	
	@Transient
	protected String message;
	
	@ManyToMany(cascade=CascadeType.ALL, mappedBy="createdNotificaitons")
	private List<User> creatorUsers;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
	
	@Column(nullable = false)
	private boolean checked = false;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}


	public List<User> getCreatorUsers() {
		return creatorUsers;
	}

	public void setCreatorUsers(List<User> creatorUsers) {
		this.creatorUsers = creatorUsers;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
