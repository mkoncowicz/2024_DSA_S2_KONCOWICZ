package org.example.photospherebackend.repositories;

import org.example.photospherebackend.models.CommentReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentReactionRepository extends JpaRepository<CommentReaction, Long> {
    Optional<CommentReaction> findByCommentIdAndUserId(Long commentId, Long userId);
    List<CommentReaction> findByCommentId(Long commentId);
    List<CommentReaction> findByUserId(Long userId);

    @Query("SELECT cr.reaction, COUNT(cr.reaction) FROM CommentReaction cr WHERE cr.comment.id = :commentId GROUP BY cr.reaction")
    List<Object[]> countReactionsByCommentId(@Param("commentId") Long commentId);
}
