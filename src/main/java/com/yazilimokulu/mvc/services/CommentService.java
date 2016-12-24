package com.yazilimokulu.mvc.services;

import com.yazilimokulu.mvc.controllers.ForbiddenException;
import com.yazilimokulu.mvc.entities.Comment;
import com.yazilimokulu.mvc.entities.Post;

public interface CommentService {

    Comment getComment(Long id);

    Long saveNewComment(Comment comment, Post post, Long parentId);

    void deleteComment(Long commentId) throws ActionExpiredException;

    void updateComment(Comment newCommentData, Long commentId) throws ActionExpiredException;

    void vote(Long commentId, boolean like) throws AlreadyVotedException, ForbiddenException;
}
