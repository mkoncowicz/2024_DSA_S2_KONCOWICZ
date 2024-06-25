package org.example.photospherebackend.controllers;

import org.example.photospherebackend.DTOs.PostReactionDTO;
import org.example.photospherebackend.models.AppUser;
import org.example.photospherebackend.models.Post;
import org.example.photospherebackend.models.PostReaction;
import org.example.photospherebackend.services.PostReactionService;
import org.example.photospherebackend.services.PostService;
import org.example.photospherebackend.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;
import java.lang.reflect.Field;
import org.springframework.util.ReflectionUtils;

@RestController
@RequestMapping("/api/post-reactions")
public class PostReactionController {

    private final PostReactionService postReactionService;
    private final PostService postService;
    private final AppUserService appUserService;

    @Autowired
    public PostReactionController(PostReactionService postReactionService, PostService postService, AppUserService appUserService) {
        this.postReactionService = postReactionService;
        this.postService = postService;
        this.appUserService = appUserService;
    }

    @GetMapping
    public List<PostReactionDTO> getAllPostReactions() {
        return postReactionService.getAllPostReactions().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostReactionDTO> getPostReactionById(@PathVariable Long id) {
        Optional<PostReaction> postReaction = postReactionService.getPostReactionById(id);
        if (postReaction.isPresent()) {
            return ResponseEntity.ok(convertToDTO(postReaction.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PostReactionDTO> createOrUpdatePostReaction(@RequestBody PostReactionDTO postReactionDTO) {
        Optional<Post> post = postService.getPostById(postReactionDTO.getPostId());
        Optional<AppUser> user = appUserService.getUserById(postReactionDTO.getUserId());
        if (post.isPresent() && user.isPresent()) {
            PostReaction postReaction = convertToEntity(postReactionDTO);
            postReaction.setPost(post.get());
            postReaction.setUser(user.get());
            postReaction.setCreatedAt(Instant.now());
            PostReaction createdOrUpdatedPostReaction = postReactionService.createOrUpdatePostReaction(postReaction);
            return ResponseEntity.ok(convertToDTO(createdOrUpdatedPostReaction));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostReactionDTO> updatePostReaction(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<PostReaction> existingPostReactionOpt = postReactionService.getPostReactionById(id);
        if (!existingPostReactionOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        PostReaction existingPostReaction = existingPostReactionOpt.get();

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(PostReaction.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingPostReaction, value);
            }
        });

        PostReaction updatedPostReaction = postReactionService.createOrUpdatePostReaction(existingPostReaction);
        return ResponseEntity.ok(convertToDTO(updatedPostReaction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostReaction(@PathVariable Long id) {
        if (postReactionService.getPostReactionById(id).isPresent()) {
            postReactionService.deletePostReactionById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<PostReactionDTO>> getPostReactionsByPostId(@PathVariable Long postId) {
        List<PostReaction> postReactions = postReactionService.getPostReactionsByPostId(postId);
        List<PostReactionDTO> postReactionDTOs = postReactions.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(postReactionDTOs);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostReactionDTO>> getPostReactionsByUserId(@PathVariable Long userId) {
        List<PostReaction> postReactions = postReactionService.getPostReactionsByUserId(userId);
        List<PostReactionDTO> postReactionDTOs = postReactions.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(postReactionDTOs);
    }

    @GetMapping("/post/{postId}/reaction-summary")
    public ResponseEntity<Map<String, Long>> getReactionSummaryByPostId(@PathVariable Long postId) {
        Map<String, Long> reactionSummary = postReactionService.getReactionCountsByPostId(postId);
        Map<String, Long> orderedReactionSummary = new HashMap<>();
        orderedReactionSummary.put("like", reactionSummary.getOrDefault("like", 0L));
        orderedReactionSummary.put("dislike", reactionSummary.getOrDefault("dislike", 0L));
        orderedReactionSummary.put("funny", reactionSummary.getOrDefault("funny", 0L));
        orderedReactionSummary.put("shocked", reactionSummary.getOrDefault("shocked", 0L));
        orderedReactionSummary.put("sad", reactionSummary.getOrDefault("sad", 0L));
        return ResponseEntity.ok(orderedReactionSummary);
    }

    private PostReactionDTO convertToDTO(PostReaction postReaction) {
        PostReactionDTO postReactionDTO = new PostReactionDTO();
        postReactionDTO.setId(postReaction.getId());
        postReactionDTO.setPostId(postReaction.getPost().getId());
        postReactionDTO.setUserId(postReaction.getUser().getId());
        postReactionDTO.setReaction(postReaction.getReaction());
        postReactionDTO.setCreatedAt(postReaction.getCreatedAt());
        return postReactionDTO;
    }

    private PostReaction convertToEntity(PostReactionDTO postReactionDTO) {
        PostReaction postReaction = new PostReaction();
        postReaction.setId(postReactionDTO.getId());
        postReaction.setReaction(postReactionDTO.getReaction());
        postReaction.setCreatedAt(postReactionDTO.getCreatedAt());
        return postReaction;
    }
}