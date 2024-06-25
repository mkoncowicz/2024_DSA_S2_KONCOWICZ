package org.example.photospherebackend.models;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "comment_reaction", uniqueConstraints = {@UniqueConstraint(columnNames = {"comment_id", "user_id", "reaction"})})
public class CommentReaction {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "comment_id", nullable = false)
        private Comment comment;

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private AppUser user;

        @Column(nullable = false)
        private String reaction;

        @Column(nullable = false)
        private Instant createdAt;

        // Getters and Setters

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Comment getComment() {
            return comment;
        }

        public void setComment(Comment comment) {
            this.comment = comment;
        }

        public AppUser getUser() {
            return user;
        }

        public void setUser(AppUser user) {
            this.user = user;
        }

        public String getReaction() {
            return reaction;
        }

        public void setReaction(String reaction) {
            this.reaction = reaction;
        }

        public Instant getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Instant createdAt) {
            this.createdAt = createdAt;
        }
    }

