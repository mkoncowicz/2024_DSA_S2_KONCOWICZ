package org.example.photospherebackend.controllers;

import org.example.photospherebackend.DTOs.CommentDTO;
import org.example.photospherebackend.models.AppUser;
import org.example.photospherebackend.models.Comment;
import org.example.photospherebackend.models.Post;
import org.example.photospherebackend.services.CommentService;
import org.example.photospherebackend.services.PostService;
import org.example.photospherebackend.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final AppUserService appUserService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService, AppUserService appUserService) {
        this.commentService = commentService;
        this.postService = postService;
        this.appUserService = appUserService;
    }

    @GetMapping
    public List<CommentDTO> getAllComments() {
        return commentService.getAllComments().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        Optional<Comment> comment = commentService.getCommentById(id);
        if (comment.isPresent()) {
            return ResponseEntity.ok(convertToDTO(comment.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) {
        Optional<Post> post = postService.getPostById(commentDTO.getPostId());
        Optional<AppUser> user = appUserService.getUserById(commentDTO.getUserId());
        if (post.isPresent() && user.isPresent()) {
            Comment comment = convertToEntity(commentDTO);
            comment.setPost(post.get());
            comment.setUser(user.get());
            comment.setCreatedAt(Instant.now());
            Comment createdComment = commentService.createComment(comment);
            return ResponseEntity.ok(convertToDTO(createdComment));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Comment> existingCommentOpt = commentService.getCommentById(id);
        if (!existingCommentOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Comment existingComment = existingCommentOpt.get();

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Comment.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingComment, value);
            }
        });

        Comment updatedComment = commentService.updateComment(existingComment);
        return ResponseEntity.ok(convertToDTO(updatedComment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        if (commentService.getCommentById(id).isPresent()) {
            commentService.deleteCommentById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        List<CommentDTO> commentDTOs = comments.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(commentDTOs);
    }

    private CommentDTO convertToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setPostId(comment.getPost().getId());
        commentDTO.setUserId(comment.getUser().getId());
        commentDTO.setText(comment.getText());
        commentDTO.setCreatedAt(comment.getCreatedAt());
        commentDTO.setNickname(comment.getUser().getNickname());
        return commentDTO;
    }

    private Comment convertToEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setText(commentDTO.getText());
        comment.setCreatedAt(commentDTO.getCreatedAt());
        return comment;
    }
}
