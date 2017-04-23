package com.yazilimokulu.mvc.services.elastic;

import com.yazilimokulu.mvc.entities.Post;

import java.util.List;

/**
 * Created by edsoft on 08.04.2017.
 */
public interface ElasticPostService {
    List<Post> findPostByTitlePosts(String title);

    List<Post> findPostByFullPostTextPosts(String text);
}
