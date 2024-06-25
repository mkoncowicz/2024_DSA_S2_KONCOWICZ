package org.example.photospherebackend.controllers;
import org.example.photospherebackend.DTOs.PostDTO;
import org.example.photospherebackend.DTOs.PostTagDTO;
import org.example.photospherebackend.DTOs.TagDTO;
import org.example.photospherebackend.models.*;
import org.example.photospherebackend.services.PostService;
import org.example.photospherebackend.services.PostTagService;
import org.example.photospherebackend.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/post-tags")
public class PostTagController {

    private final PostTagService postTagService;
    private final PostService postService;
    private final TagService tagService;
    private final PostController postController;

    @Autowired
    public PostTagController(PostTagService postTagService, PostService postService, TagService tagService, PostController postController) {
        this.postTagService = postTagService;
        this.postService = postService;
        this.tagService = tagService;
        this.postController = postController;
    }

    @GetMapping
    public List<PostTagDTO> getAllPostTags() {
        return postTagService.getAllPostTags().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{postId}/{tagId}")
    public ResponseEntity<PostTagDTO> getPostTagById(@PathVariable Long postId, @PathVariable Long tagId) {
        PostTagId id = new PostTagId();
        id.setPostId(postId);
        id.setTagId(tagId);
        Optional<PostTag> postTag = postTagService.getPostTagById(id);
        if (postTag.isPresent()) {
            return ResponseEntity.ok(convertToDTO(postTag.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PostTagDTO> createPostTag(@RequestBody PostTagDTO postTagDTO) {
        Optional<Post> postOpt = postService.getPostById(postTagDTO.getPostId());
        Optional<Tag> tagOpt = tagService.getTagById(postTagDTO.getTagId());

        if (postOpt.isPresent() && tagOpt.isPresent()) {
            PostTag postTag = convertToEntity(postTagDTO);
            postTag.setPost(postOpt.get());
            postTag.setTag(tagOpt.get());
            PostTag createdPostTag = postTagService.createPostTag(postTag);
            return ResponseEntity.ok(convertToDTO(createdPostTag));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{postId}/{tagId}")
    public ResponseEntity<Void> deletePostTag(@PathVariable Long postId, @PathVariable Long tagId) {
        PostTagId id = new PostTagId();
        id.setPostId(postId);
        id.setTagId(tagId);
        postTagService.deletePostTagById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<TagDTO>> getTagsByPostId(@PathVariable Long postId) {
        List<Tag> tags = postTagService.getTagsByPostId(postId);
        List<TagDTO> tagDTOs = tags.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(tagDTOs);
    }

    @GetMapping("/tag/{tagId}/posts")
    public ResponseEntity<List<PostDTO>> getPostsByTagId(@PathVariable Long tagId) {
        List<Post> posts = postTagService.getPostsByTagId(tagId);
        List<PostDTO> postDTOs = posts.stream().map(postController::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(postDTOs);
    }

    private PostTagDTO convertToDTO(PostTag postTag) {
        PostTagDTO postTagDTO = new PostTagDTO();
        postTagDTO.setPostId(postTag.getPost().getId());
        postTagDTO.setTagId(postTag.getTag().getId());
        return postTagDTO;
    }

    private PostTag convertToEntity(PostTagDTO postTagDTO) {
        PostTag postTag = new PostTag();
        PostTagId id = new PostTagId();
        id.setPostId(postTagDTO.getPostId());
        id.setTagId(postTagDTO.getTagId());
        postTag.setId(id);
        return postTag;
    }

    private TagDTO convertToDTO(Tag tag) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());
        return tagDTO;
    }
}