package org.example.photospherebackend.repositories;

import org.example.photospherebackend.models.PostReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostReactionRepository extends JpaRepository<PostReaction, Long> {
    List<PostReaction> findByPostId(Long postId);
    List<PostReaction> findByUserId(Long userId);
    Optional<PostReaction> findByPostIdAndUserId(Long postId, Long userId);

    @Query("SELECT pr.reaction, COUNT(pr.reaction) FROM PostReaction pr WHERE pr.post.id = :postId GROUP BY pr.reaction")
    List<Object[]> countReactionsByPostId(@Param("postId") Long postId);
}
