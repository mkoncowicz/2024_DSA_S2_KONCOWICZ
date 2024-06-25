package org.example.photospherebackend.services;

import org.example.photospherebackend.models.PostReaction;
import org.example.photospherebackend.repositories.PostReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PostReactionService {

    private final PostReactionRepository postReactionRepository;

    @Autowired
    public PostReactionService(PostReactionRepository postReactionRepository) {
        this.postReactionRepository = postReactionRepository;
    }

    public List<PostReaction> getAllPostReactions() {
        return postReactionRepository.findAll();
    }

    public Optional<PostReaction> getPostReactionById(Long id) {
        return postReactionRepository.findById(id);
    }

    public Optional<PostReaction> getPostReactionByPostIdAndUserId(Long postId, Long userId) {
        return postReactionRepository.findByPostIdAndUserId(postId, userId);
    }

    public PostReaction createOrUpdatePostReaction(PostReaction postReaction) {
        Optional<PostReaction> existingReaction = postReactionRepository.findByPostIdAndUserId(postReaction.getPost().getId(), postReaction.getUser().getId());
        if (existingReaction.isPresent()) {
            PostReaction existing = existingReaction.get();
            existing.setReaction(postReaction.getReaction());
            existing.setCreatedAt(postReaction.getCreatedAt());
            return postReactionRepository.save(existing);
        } else {
            return postReactionRepository.save(postReaction);
        }
    }

    public void deletePostReactionById(Long id) {
        postReactionRepository.deleteById(id);
    }

    public List<PostReaction> getPostReactionsByPostId(Long postId) {
        return postReactionRepository.findByPostId(postId);
    }

    public List<PostReaction> getPostReactionsByUserId(Long userId) {
        return postReactionRepository.findByUserId(userId);
    }

    public Map<String, Long> getReactionCountsByPostId(Long postId) {
        List<Object[]> reactionCounts = postReactionRepository.countReactionsByPostId(postId);
        Map<String, Long> reactionCountMap = new HashMap<>();
        for (Object[] reactionCount : reactionCounts) {
            reactionCountMap.put((String) reactionCount[0], (Long) reactionCount[1]);
        }
        return reactionCountMap;
    }
}
