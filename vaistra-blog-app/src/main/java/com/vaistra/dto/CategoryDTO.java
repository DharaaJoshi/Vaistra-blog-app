package com.vaistra.dto;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Integer categoryId;

    @NotEmpty(message = "Category name should not be Empty!")
    @NotBlank(message = "Category name should not be Blank!")
    @Size(min = 3, message = "Category name should be at least 3 characters!")
    private String categoryName;

    @Size(max = 100, message = "Category description should not be exceed than 100 characters!")
    private String categoryDescription;

    private boolean isActive;
    private boolean isDeleted;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;
}
