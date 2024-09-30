package com.krushit.service;

import com.krushit.model.CommentModel;

import java.util.List;
import java.util.Optional;

public interface ICommentService {

    CommentModel createComment(CommentModel commentModel);

    CommentModel updateComment(CommentModel commentModel);

    Optional<CommentModel> getCommentById(Long id);

    void deleteComment(Long id);

    List<CommentModel> getAllComments();
}
