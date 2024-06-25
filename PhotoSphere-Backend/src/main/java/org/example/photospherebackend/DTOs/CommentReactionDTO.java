package org.example.photospherebackend.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class CommentReactionDTO {

    private Long id;
    private Long commentId;
    private Long userId;
    private String reaction;
    private Instant createdAt;
}
