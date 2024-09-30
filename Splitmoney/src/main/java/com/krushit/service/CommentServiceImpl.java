package com.krushit.service;

import com.krushit.entity.Comment;
import com.krushit.model.CommentModel;
import com.krushit.repository.CommentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements ICommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public CommentModel createComment(CommentModel commentModel) {
		// Convert CommentModel to Comment entity
		Comment comment = new Comment();
		BeanUtils.copyProperties(commentModel, comment);

		// Save the entity
		Comment savedComment = commentRepository.save(comment);

		// Convert the saved Comment entity back to CommentModel
		CommentModel savedCommentModel = new CommentModel();
		BeanUtils.copyProperties(savedComment, savedCommentModel);

		return savedCommentModel;
	}

	@Override
	public CommentModel updateComment(CommentModel commentModel) {
		Optional<Comment> optionalComment = commentRepository.findById(commentModel.getId());
		if (optionalComment.isPresent()) {
			Comment comment = optionalComment.get();
			// Update the entity with properties from the model
			BeanUtils.copyProperties(commentModel, comment);

			// Save the updated entity
			Comment updatedComment = commentRepository.save(comment);

			// Convert the updated entity back to CommentModel
			CommentModel updatedCommentModel = new CommentModel();
			BeanUtils.copyProperties(updatedComment, updatedCommentModel);

			return updatedCommentModel;
		} else {
			// Handle the case where the comment is not found
			return null;
		}
	}

	@Override
	public Optional<CommentModel> getCommentById(Long id) {
		Optional<Comment> optionalComment = commentRepository.findById(id);
		if (optionalComment.isPresent()) {
			// Convert entity to model
			CommentModel commentModel = new CommentModel();
			BeanUtils.copyProperties(optionalComment.get(), commentModel);
			return Optional.of(commentModel);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteComment(Long id) {
		commentRepository.deleteById(id);
	}

	@Override
	public List<CommentModel> getAllComments() {
		// Fetch all entities and convert them to CommentModel
		return commentRepository.findAll().stream().map(comment -> {
			CommentModel commentModel = new CommentModel();
			BeanUtils.copyProperties(comment, commentModel);
			return commentModel;
		}).collect(Collectors.toList());
	}
}
