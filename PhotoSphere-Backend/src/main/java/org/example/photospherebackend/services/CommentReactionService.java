package org.example.photospherebackend.services;

import org.example.photospherebackend.models.CommentReaction;
import org.example.photospherebackend.repositories.CommentReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CommentReactionService {

    private final CommentReactionRepository commentReactionRepository;

    @Autowired
    public CommentReactionService(CommentReactionRepository commentReactionRepository) {
        this.commentReactionRepository = commentReactionRepository;
    }

    public List<CommentReaction> getAllCommentReactions() {
        return commentReactionRepository.findAll();
    }

    public Optional<CommentReaction> getCommentReactionById(Long id) {
        return commentReactionRepository.findById(id);
    }

    public Optional<CommentReaction> getCommentReactionByCommentIdAndUserId(Long commentId, Long userId) {
        return commentReactionRepository.findByCommentIdAndUserId(commentId, userId);
    }

    public CommentReaction createOrUpdateCommentReaction(CommentReaction commentReaction) {
        Optional<CommentReaction> existingReaction = commentReactionRepository.findByCommentIdAndUserId(commentReaction.getComment().getId(), commentReaction.getUser().getId());
        if (existingReaction.isPresent()) {
            CommentReaction existing = existingReaction.get();
            existing.setReaction(commentReaction.getReaction());
            existing.setCreatedAt(commentReaction.getCreatedAt());
            return commentReactionRepository.save(existing);
        } else {
            return commentReactionRepository.save(commentReaction);
        }
    }

    public void deleteCommentReactionById(Long id) {
        commentReactionRepository.deleteById(id);
    }

    public List<CommentReaction> getCommentReactionsByCommentId(Long commentId) {
        return commentReactionRepository.findByCommentId(commentId);
    }

    public List<CommentReaction> getCommentReactionsByUserId(Long userId) {
        return commentReactionRepository.findByUserId(userId);
    }

    public Map<String, Long> getReactionCountsByCommentId(Long commentId) {
        List<Object[]> reactionCounts = commentReactionRepository.countReactionsByCommentId(commentId);
        Map<String, Long> reactionCountMap = new HashMap<>();
        for (Object[] reactionCount : reactionCounts) {
            reactionCountMap.put((String) reactionCount[0], (Long) reactionCount[1]);
        }
        return reactionCountMap;
    }
}
