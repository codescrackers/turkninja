package com.yazilimokulu.mvc.services.elastic;

import com.yazilimokulu.mvc.daos.elastic.ElasticPostRepository;
import com.yazilimokulu.mvc.entities.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by edsoft on 08.04.2017.
 */
@Service
public class ElasticPostServiceImpl implements ElasticPostService {

    ElasticPostRepository elasticPostRepository;

    @Autowired
    public ElasticPostServiceImpl(ElasticPostRepository elasticPostRepository) {
        this.elasticPostRepository = elasticPostRepository;
    }

    @Override
    public List<Post> findPostByTitlePosts(String title) {
        return elasticPostRepository.findByTitle(title);
    }

    @Override
    public List<Post> findPostByFullPostTextPosts(String text) {
        return elasticPostRepository.findByFullPostText(text);
    }
}
