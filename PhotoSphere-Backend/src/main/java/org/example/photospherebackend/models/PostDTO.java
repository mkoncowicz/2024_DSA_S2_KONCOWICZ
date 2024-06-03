package org.example.photospherebackend.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private String caption;
    private String imageUrl;
    private String category;
    private String description;
    private boolean isPrivate;
    private Long userId;
}
