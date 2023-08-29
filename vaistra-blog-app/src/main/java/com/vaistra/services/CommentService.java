package com.vaistra.services;

import com.vaistra.dto.CommentDTO;
import com.vaistra.exception.ResourceNotFoundException;

import java.util.List;

public interface CommentService {
    public CommentDTO addComment(CommentDTO commentDTO);

    public List<CommentDTO> getAllComment(int pageNo, int pageSize, String sortBy, String sortDirection);

    public CommentDTO getCommentById(int id);

    public CommentDTO updateComment(CommentDTO commentDTO, int id);

    public String softDeleteComment(int id);

    public String hardDeleteComment(int id) throws ResourceNotFoundException;

    public String restoreComment(int id);

    public String getUserNameByComment(int id);

    public List<CommentDTO> getCommentsByPost(int postId, int pageNo, int pageSize, String sortBy, String sortDirection);

    public List<CommentDTO> getCommentsByUser(int userId, int pageNo, int pageSize, String sortBy, String sortDirection);

}