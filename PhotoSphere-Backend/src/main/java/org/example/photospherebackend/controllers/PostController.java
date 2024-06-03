package org.example.photospherebackend.controllers;

import org.example.photospherebackend.models.AppUser;
import org.example.photospherebackend.models.Post;
import org.example.photospherebackend.models.PostDTO;
import org.example.photospherebackend.services.AppUserService;
import org.example.photospherebackend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.lang.reflect.Field;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final AppUserService appUserService;

    @Autowired
    public PostController(PostService postService, AppUserService appUserService) {
        this.postService = postService;
        this.appUserService = appUserService;
    }

    @GetMapping
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        Optional<Post> post = postService.getPostById(id);
        if (post.isPresent()) {
            return ResponseEntity.ok(convertToDTO(post.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        Optional<AppUser> user = appUserService.getUserById(postDTO.getUserId());
        if (user.isPresent()) {
            Post post = convertToEntity(postDTO);
            post.setUser(user.get());
            Post createdPost = postService.createPost(post);
            return ResponseEntity.ok(convertToDTO(createdPost));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Post> existingPostOpt = postService.getPostById(id);
        if (!existingPostOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Post existingPost = existingPostOpt.get();

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Post.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingPost, value);
            }
        });

        Post updatedPost = postService.updatePost(existingPost);
        return ResponseEntity.ok(convertToDTO(updatedPost));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        if (postService.getPostById(id).isPresent()) {
            postService.deletePostById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadPostImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) throws IOException {
        Optional<Post> existingPost = postService.getPostById(id);
        if (existingPost.isPresent()) {
            postService.deletePostImage(existingPost.get().getId());
            Long userId = existingPost.get().getUser().getId();
            String imageUrl = postService.uploadPostImage(image, id, userId);
            existingPost.get().setImageUrl(imageUrl);
            postService.updatePostImageUrl(id, imageUrl);
            return ResponseEntity.ok("Image uploaded successfully: " + imageUrl);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/delete-image")
    public ResponseEntity<String> deletePostImage(@PathVariable Long id) {
        Optional<Post> existingPost = postService.getPostById(id);
        if (existingPost.isPresent()) {
            postService.deletePostImage(existingPost.get().getId());
            return ResponseEntity.ok("Image deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/download-image")
    public ResponseEntity<byte[]> downloadPostImage(@PathVariable Long id) {
        Optional<Post> existingPost = postService.getPostById(id);
        if (existingPost.isPresent()) {
            String imagePath = existingPost.get().getImageUrl();
            if (imagePath != null && !imagePath.isEmpty()) {
                byte[] imageBytes = postService.downloadPostImage(imagePath);
                String mimeType = postService.getMimeType(imagePath);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(mimeType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imagePath.substring(imagePath.lastIndexOf('/') + 1) + "\"")
                        .body(imageBytes);
            } else {
                return ResponseEntity.noContent().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private PostDTO convertToDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setUserId(post.getUser().getId());
        postDTO.setCaption(post.getCaption());
        postDTO.setImageUrl(post.getImageUrl());
        postDTO.setCategory(post.getCategory());
        postDTO.setDescription(post.getDescription());
        postDTO.setPrivate(post.isPrivate());
        return postDTO;
    }

    private Post convertToEntity(PostDTO postDTO) {
        Post post = new Post();
        post.setId(postDTO.getId());
        post.setCaption(postDTO.getCaption());
        post.setImageUrl(postDTO.getImageUrl());
        post.setCategory(postDTO.getCategory());
        post.setDescription(postDTO.getDescription());
        post.setPrivate(postDTO.isPrivate());
        return post;
    }

    @GetMapping("/owner/{userId}")
    public ResponseEntity<List<Long>> getPostIdsByUserId(@PathVariable Long userId) {
        List<Post> posts = postService.getPostsByUserId(userId);
        List<Long> postIds = posts.stream()
                .map(Post::getId)
                .collect(Collectors.toList());
        return ResponseEntity.ok(postIds);
    }
}