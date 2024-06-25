package org.example.photospherebackend.controllers;

import org.example.photospherebackend.DTOs.CommentReactionDTO;
import org.example.photospherebackend.models.AppUser;
import org.example.photospherebackend.models.Comment;
import org.example.photospherebackend.models.CommentReaction;
import org.example.photospherebackend.services.CommentReactionService;
import org.example.photospherebackend.services.CommentService;
import org.example.photospherebackend.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.lang.reflect.Field;
import org.springframework.util.ReflectionUtils;

@RestController
@RequestMapping("/api/comment-reactions")
public class CommentReactionController {

    private final CommentReactionService commentReactionService;
    private final CommentService commentService;
    private final AppUserService appUserService;

    @Autowired
    public CommentReactionController(CommentReactionService commentReactionService, CommentService commentService, AppUserService appUserService) {
        this.commentReactionService = commentReactionService;
        this.commentService = commentService;
        this.appUserService = appUserService;
    }

    @GetMapping
    public List<CommentReactionDTO> getAllCommentReactions() {
        return commentReactionService.getAllCommentReactions().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentReactionDTO> getCommentReactionById(@PathVariable Long id) {
        Optional<CommentReaction> commentReaction = commentReactionService.getCommentReactionById(id);
        if (commentReaction.isPresent()) {
            return ResponseEntity.ok(convertToDTO(commentReaction.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CommentReactionDTO> createOrUpdateCommentReaction(@RequestBody CommentReactionDTO commentReactionDTO) {
        Optional<Comment> comment = commentService.getCommentById(commentReactionDTO.getCommentId());
        Optional<AppUser> user = appUserService.getUserById(commentReactionDTO.getUserId());
        if (comment.isPresent() && user.isPresent()) {
            CommentReaction commentReaction = convertToEntity(commentReactionDTO);
            commentReaction.setComment(comment.get());
            commentReaction.setUser(user.get());
            commentReaction.setCreatedAt(Instant.now());
            CommentReaction createdOrUpdatedCommentReaction = commentReactionService.createOrUpdateCommentReaction(commentReaction);
            return ResponseEntity.ok(convertToDTO(createdOrUpdatedCommentReaction));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentReaction(@PathVariable Long id) {
        if (commentReactionService.getCommentReactionById(id).isPresent()) {
            commentReactionService.deleteCommentReactionById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<List<CommentReactionDTO>> getCommentReactionsByCommentId(@PathVariable Long commentId) {
        List<CommentReaction> commentReactions = commentReactionService.getCommentReactionsByCommentId(commentId);
        List<CommentReactionDTO> commentReactionDTOs = commentReactions.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(commentReactionDTOs);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentReactionDTO>> getCommentReactionsByUserId(@PathVariable Long userId) {
        List<CommentReaction> commentReactions = commentReactionService.getCommentReactionsByUserId(userId);
        List<CommentReactionDTO> commentReactionDTOs = commentReactions.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(commentReactionDTOs);
    }

    @GetMapping("/comment/{commentId}/reaction-summary")
    public ResponseEntity<Map<String, Long>> getReactionSummaryByCommentId(@PathVariable Long commentId) {
        Map<String, Long> reactionSummary = commentReactionService.getReactionCountsByCommentId(commentId);
        Map<String, Long> orderedReactionSummary = new HashMap<>();
        orderedReactionSummary.put("like", reactionSummary.getOrDefault("like", 0L));
        orderedReactionSummary.put("dislike", reactionSummary.getOrDefault("dislike", 0L));
        orderedReactionSummary.put("funny", reactionSummary.getOrDefault("funny", 0L));
        orderedReactionSummary.put("shocked", reactionSummary.getOrDefault("shocked", 0L));
        orderedReactionSummary.put("sad", reactionSummary.getOrDefault("sad", 0L));
        return ResponseEntity.ok(orderedReactionSummary);
    }

    private CommentReactionDTO convertToDTO(CommentReaction commentReaction) {
        CommentReactionDTO commentReactionDTO = new CommentReactionDTO();
        commentReactionDTO.setId(commentReaction.getId());
        commentReactionDTO.setCommentId(commentReaction.getComment().getId());
        commentReactionDTO.setUserId(commentReaction.getUser().getId());
        commentReactionDTO.setReaction(commentReaction.getReaction());
        commentReactionDTO.setCreatedAt(commentReaction.getCreatedAt());
        return commentReactionDTO;
    }

    private CommentReaction convertToEntity(CommentReactionDTO commentReactionDTO) {
        CommentReaction commentReaction = new CommentReaction();
        commentReaction.setId(commentReactionDTO.getId());
        commentReaction.setReaction(commentReactionDTO.getReaction());
        commentReaction.setCreatedAt(commentReactionDTO.getCreatedAt());
        return commentReaction;
    }
}
