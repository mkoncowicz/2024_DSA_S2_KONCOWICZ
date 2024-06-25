package org.example.photospherebackend.repositories;

import org.example.photospherebackend.models.Post;
import org.example.photospherebackend.models.PostTag;
import org.example.photospherebackend.models.PostTagId;
import org.example.photospherebackend.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, PostTagId> {
    @Query("SELECT pt.tag FROM PostTag pt WHERE pt.post.id = :postId")
    List<Tag> findTagsByPostId(@Param("postId") Long postId);

    @Query("SELECT pt.post FROM PostTag pt WHERE pt.tag.id = :tagId")
    List<Post> findPostsByTagId(@Param("tagId") Long tagId);
}
