package org.example.photospherebackend.auth;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder

public class RegistrationRequest {
    @NotEmpty(message = "nickname is mandatory")
    @NotBlank(message = "nickname is mandatory")
    private String nickname;
    @NotEmpty(message = "First name is mandatory")
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotEmpty(message = "Last name is mandatory")
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    @Email(message = "Email have wrong format")
    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    private String email;
    @NotEmpty(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotEmpty(message = "Gender is mandatory")
    @NotBlank(message = "Gender is mandatory")
    private String gender;
    @NotNull(message = "Birth date is mandatory")
    private LocalDate dayOfBirth;
}
