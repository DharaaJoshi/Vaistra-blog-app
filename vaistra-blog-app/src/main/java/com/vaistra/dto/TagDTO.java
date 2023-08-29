package com.vaistra.dto;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {

    private Integer tagId;

    @NotEmpty(message = "Tag name should not be Empty!")
    @NotBlank(message = "Tag name should not be Blank!")
    @Size(min = 3, message = "Tag name should be at least 3 characters!")
    private String tagName;

}
