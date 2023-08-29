package com.vaistra.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tagId;

    private String tagName;

    private boolean isActive;

    private boolean isDeleted;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tag_posts",
            joinColumns = @JoinColumn(name = "tagId"),
            inverseJoinColumns = @JoinColumn(name = "postId")
    )
    private List<Post> posts = new ArrayList<>();

    private LocalDateTime createdAt;

}
