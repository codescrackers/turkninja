package com.yazilimokulu.mvc.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;



@Entity
@Table(name = "skills")
public class Skill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@Column(length = 30, nullable = false, unique = true)
	private String name;

	@ManyToMany(mappedBy = "skills")
	private Collection<User> users = new ArrayList<>();

	public Skill() {

	}

	public Skill(String name) {
		this.name=name;
	}
	
	 public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}
	

	@Override
	    public String toString() {
	        return "Skill{" +
	                "name='" + name + '\'' +
	                '}';
	    }
}
