package com.vaistra.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class PostDTO {
    private Integer postId;

    @NotEmpty(message = "Post shouldn't be empty.")
    @NotNull(message = "Post shouldn't be null.")
    @NotBlank(message = "Post shouldn't be blank.")
    @Size(min = 1, max = 250, message = "Post should have a length between 1 and 250 characters.")
    private String postTitle;

    @NotEmpty(message = "Post Content shouldn't be empty.")
    @NotNull(message = "Post Content shouldn't be null.")
    @NotBlank(message = "Post Content shouldn't be blank.")
    @Size(min = 1, max = 250, message = "Post Content should have a length between 1 and 250 characters.")
    private String postContent;

    private UserDTO user;

    private CategoryDTO category;

    private List<TagDTO> tags= new ArrayList<>();

    private boolean isDeleted;

    private boolean isActive;

    private LocalDateTime createdAt;
}
