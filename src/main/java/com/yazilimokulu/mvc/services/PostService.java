package com.yazilimokulu.mvc.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yazilimokulu.mvc.entities.Post;
import com.yazilimokulu.mvc.entities.PostEditDto;

public interface PostService {

    Page<Post> getPostsPage(int pageNumber, int pageSize);

    List<Post> getPostsList(int pageNumber, int pageSize);
    
    Page<Post> getPostsPageByUsername(String username,int pageNumber, int pageSize);

    List<Post> getTopPostsList();

    Post getPost(Long id);

    PostEditDto getEditablePost(Long id);

    Page<Post> findPostsByTag(List<String> tags, int pageNumber, int pageSize);

    Post saveNewPost(PostEditDto postEditDto);

    Post updatePost(PostEditDto postEditDto);

    void setPostVisibility(Long postId, boolean hide);

    void deletePost(Long postId);

    void vote(Long postId, boolean like) throws AlreadyVotedException;
    
    List<Post> getMainpagePostList();
}
