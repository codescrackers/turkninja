package com.yazilimokulu.mvc.entities;

import org.springframework.beans.factory.annotation.Autowired;

import com.yazilimokulu.mvc.entities.Skill;

public class SkillWithUserCount {
	private Long userCount;
	@Autowired
	private Skill skill;

	public SkillWithUserCount(Long userCount, Skill skill) {
		this.userCount = userCount;
		this.skill = skill;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public Long getUserCount() {
		return userCount;
	}

	public void setUserCount(Long userCount) {
		this.userCount = userCount;
	}

}