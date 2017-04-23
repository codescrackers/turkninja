package com.yazilimokulu.mvc.daos.elastic;

import com.yazilimokulu.mvc.entities.Post;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import java.util.List;

/**
 * Created by edsoft on 08.04.2017.
 */
public interface ElasticPostRepository extends /*ElasticsearchRepository<Post, Long>*/ElasticsearchCrudRepository<Post, Long> {
    List<Post> findByTitle(String title);

    List<Post> findByFullPostText(String fullPostText);

}
