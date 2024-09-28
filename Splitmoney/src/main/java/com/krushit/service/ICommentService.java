package com.krushit.service;

import com.krushit.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface ICommentService {

    Comment createComment(Comment comment);

    Comment updateComment(Comment comment);

    Optional<Comment> getCommentById(Long id);

    void deleteComment(Long id);

    List<Comment> getAllComments();
}
