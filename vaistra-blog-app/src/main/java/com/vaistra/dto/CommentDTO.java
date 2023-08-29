package com.vaistra.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Integer commentId;

    @NotEmpty(message = "Comment shouldn't be empty.")
    @NotNull(message = "Comment shouldn't be null.")
    @NotBlank(message = "Comment shouldn't be blank.")
    @Size(min = 1, max = 250, message = "Comment should have a length between 1 and 250 characters.")

    private String comment;

    private PostDTO post;

    private boolean deleted;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}