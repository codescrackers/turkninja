package com.yazilimokulu.mvc.daos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.yazilimokulu.mvc.entities.Skill;
import com.yazilimokulu.mvc.entities.SkillWithUserCount;

public interface SkillRepository extends BaseRepository<Skill, Long> {

    Skill findByNameIgnoreCase(String name);
    List<Skill> findAll();
    
    @Query(
            value = "select new com.yazilimokulu.mvc.entities.SkillWithUserCount(count(user.id) as userCount,skill) from Skill skill join skill.users user group by skill order by userCount desc",
            countQuery = "select count(distinct skill) from Skill skill join skill.users user "
        )
        Page<SkillWithUserCount> findAllWithUserCount(Pageable pageable);
}
