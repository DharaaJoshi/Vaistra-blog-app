package com.vaistra.repositories;

import com.vaistra.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("SELECT c FROM Comment c where c.deleted = false AND c.commentId = :id")
    Comment findCommentByUserDefineId(@Param("id") int stateId);

    Page<Comment> findCommentByPostPostId(int id, Pageable p);

    Page<Comment> findCommentByUserUserId(int id, Pageable p);
}
