package org.example.photospherebackend.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private String caption;
    private String imageUrl;
    private String category;
    private String description;
    private boolean isPrivate;
    private Instant createdAt;
    private Long userId;
}
