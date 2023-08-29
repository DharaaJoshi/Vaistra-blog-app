package com.vaistra.services;

import com.vaistra.dto.PostDTO;

import java.util.List;

public interface PostService {

    PostDTO addPost(PostDTO postDTO);
    PostDTO getPostById(Integer postId);
    List<PostDTO> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDirection);
    List<PostDTO> getAllPostsByDeleted(boolean deleted, int pageNumber, int pageSize, String sortBy, String sortDirection);
    List<PostDTO> getPostsByUserId(Integer userId, int pageNumber, int pageSize, String sortBy, String sortDirection);
    List<PostDTO> getPostsByCommentId(Integer commentId, int pageNumber, int pageSize, String sortBy, String sortDirection);
    List<PostDTO> getPostsByCategoryId(Integer categoryId, int pageNumber, int pageSize, String sortBy, String sortDirection);
    PostDTO updatePost(Integer postId, PostDTO postDTO);
    String softDeletePost(Integer postId);
    String hardDeletePost(Integer postId);
    String restorePost(Integer postId);
}
