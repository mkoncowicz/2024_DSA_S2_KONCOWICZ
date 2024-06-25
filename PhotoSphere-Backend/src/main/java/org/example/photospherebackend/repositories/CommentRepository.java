package org.example.photospherebackend.repositories;

import org.example.photospherebackend.models.Comment;
import org.example.photospherebackend.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
}