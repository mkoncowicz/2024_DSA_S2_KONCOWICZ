package org.example.photospherebackend.services;

import org.example.photospherebackend.models.Post;
import org.example.photospherebackend.models.PostTag;
import org.example.photospherebackend.models.PostTagId;
import org.example.photospherebackend.models.Tag;
import org.example.photospherebackend.repositories.PostTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostTagService {

    private final PostTagRepository postTagRepository;

    @Autowired
    public PostTagService(PostTagRepository postTagRepository) {
        this.postTagRepository = postTagRepository;
    }

    public List<PostTag> getAllPostTags() {
        return postTagRepository.findAll();
    }

    public Optional<PostTag> getPostTagById(PostTagId id) {
        return postTagRepository.findById(id);
    }

    public PostTag createPostTag(PostTag postTag) {
        return postTagRepository.save(postTag);
    }

    public void deletePostTagById(PostTagId id) {
        postTagRepository.deleteById(id);
    }

    public List<Tag> getTagsByPostId(Long postId) {
        return postTagRepository.findTagsByPostId(postId);
    }

    public List<Post> getPostsByTagId(Long tagId) {
        return postTagRepository.findPostsByTagId(tagId);
    }
}
