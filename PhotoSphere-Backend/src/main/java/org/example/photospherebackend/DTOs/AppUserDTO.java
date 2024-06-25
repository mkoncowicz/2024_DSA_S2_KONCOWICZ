package org.example.photospherebackend.DTOs;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.photospherebackend.models.Role;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AppUserDTO {
    private Long id;
    private String nickname;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private LocalDate dayOfBirth;
    private String description;
    private String image;

}

