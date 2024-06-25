package org.example.photospherebackend.repositories;

import org.example.photospherebackend.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);
    List<Post> findByCategory(String category);

    @Query("SELECT DISTINCT p.category FROM Post p WHERE p.user.id = :userId")
    List<String> findDistinctCategoriesByUserId(@Param("userId") Long userId);

    @Query("SELECT DISTINCT p.category FROM Post p")
    List<String> findAllCategories();
}
