package com.vaistra.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer userId;

    @NotBlank
    @NotNull
    @NotEmpty
    private String username;

    @NotBlank
    @NotNull
    @NotEmpty
    private String password;

    @NotBlank
    @NotNull
    @NotEmpty
    private String firstName;

    @NotBlank
    @NotNull
    @NotEmpty
    private String lastName;

    @NotBlank
    @NotNull
    @NotEmpty
    @Email(message = "Please Enter a valid E-mail Address")
    private String email;

    private LocalDateTime createdAt;

    private String imagePath;

    private boolean isActive;

    private boolean isDeleted;

}
