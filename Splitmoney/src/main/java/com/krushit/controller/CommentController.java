package com.krushit.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krushit.model.CommentModel;
import com.krushit.service.ICommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @PostMapping("/createComment")
    public ResponseEntity<CommentModel> createComment(@RequestBody CommentModel commentModel) {
        CommentModel createdComment = commentService.createComment(commentModel);
        return ResponseEntity.ok(createdComment);
    }

    @PutMapping("/updateComment/{id}")
    public ResponseEntity<CommentModel> updateComment(@PathVariable Long id, @RequestBody CommentModel commentModel) {
        commentModel.setId(id);
        CommentModel updatedComment = commentService.updateComment(commentModel);
        return ResponseEntity.ok(updatedComment);
    }
    @GetMapping("/getComment/{id}")
    public ResponseEntity<Optional<CommentModel>> getCommentById(@PathVariable Long id) {
        Optional<CommentModel> commentModel = commentService.getCommentById(id);
        return ResponseEntity.ok(commentModel);
    }

    @DeleteMapping("/deleteComment/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAllComments")
    public ResponseEntity<List<CommentModel>> getAllComments() {
        List<CommentModel> allComments = commentService.getAllComments();
        return ResponseEntity.ok(allComments);
    }
}
