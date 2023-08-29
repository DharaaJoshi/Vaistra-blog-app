package com.vaistra.services.impl;

import com.vaistra.dto.CommentDTO;
import com.vaistra.entities.Comment;
import com.vaistra.entities.Post;
import com.vaistra.entities.User;
import com.vaistra.exception.IsActiveExceptionHandler;
import com.vaistra.exception.IsDeleteExceptionHandler;
import com.vaistra.exception.NoDataFoundException;
import com.vaistra.exception.ResourceNotFoundException;
import com.vaistra.repositories.CommentRepository;
import com.vaistra.repositories.PostRepository;
import com.vaistra.repositories.UserRepository;
import com.vaistra.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.vaistra.utils.AppUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final AppUtils appUtilsComment;


    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository, AppUtils appUtilsComment){
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.appUtilsComment = appUtilsComment;
    }

    @Override
    public CommentDTO addComment(CommentDTO commentDTO) {
        int postId = commentDTO.getPost().getPostId();
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post not found...!"));


        int userID = post.getUser().getUserId();
        User user = userRepository.findById(userID).orElseThrow(()->new ResourceNotFoundException("User not found...!"));

        commentDTO.setPost(appUtilsComment.postToDto(post));
        commentDTO.setCreatedAt(LocalDateTime.now());

        if(!post.isActive()){
            throw new IsActiveExceptionHandler("Define post is inacative.");
        }

        if(post.isDeleted()){
            throw new IsDeleteExceptionHandler("Define post is deleted");

        }
        if(!user.isActive()){
            throw new IsActiveExceptionHandler("Define user is inactive");
        }
        if(user.isDeleted()){
            throw new IsDeleteExceptionHandler("Define user is deleted");
        }

        Comment savedComment = commentRepository.save(appUtilsComment.dtoToComment(commentDTO));

        Post managedPost = postRepository.save(post);
        User managedUser = userRepository.save(user);

        managedPost.getComments().add(savedComment);
        managedUser.getComments().add(savedComment);

        savedComment.setPost(postRepository.save(managedPost));
        savedComment.setUser(userRepository.save(managedUser));

        return appUtilsComment.commentToDto(commentRepository.save(savedComment));
    }

    @Override
    public List<CommentDTO> getAllComment(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Comment> comments = commentRepository.findAll(pageable);

        if (comments.isEmpty()) {
            throw new NoDataFoundException("No Data Found...!");
        }

        return appUtilsComment.commentsToDtos(comments.getContent());
    }

    @Override
    public CommentDTO getCommentById(int id) {
        Comment comment = commentRepository.findCommentByUserDefineId(id);

        if(comment != null){
            return appUtilsComment.commentToDto(commentRepository.findCommentByUserDefineId(id));
        }
        throw new ResourceNotFoundException("Comment not found with id: " + id);
    }

    @Override
    public CommentDTO updateComment(CommentDTO commentDTO, int id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));

        comment.setComment(commentDTO.getComment());
        comment.setUpdatedAt(LocalDateTime.now());

        int postId =comment.getPost().getPostId();
        int userId =comment.getUser().getUserId();

        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("define post is not found with id: " + id));
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("define user is not found with id: " + id));

        if(!post.isActive()){
            throw new IsActiveExceptionHandler("Define post is inacative.");
        }

        if(post.isDeleted()){
            throw new IsDeleteExceptionHandler("Define post is deleted");

        }
        if(!user.isActive()){
            throw new IsActiveExceptionHandler("Define user is inactive");
        }
        if(user.isDeleted()){
            throw new IsDeleteExceptionHandler("Define user is deleted");
        }

        Comment savedComment = commentRepository.save(comment);

        Post managedPost = postRepository.save(post);
        User managedUser = userRepository.save(user);

        managedPost.getComments().add(savedComment);
        managedUser.getComments().add(savedComment);

        postRepository.save(managedPost);
        userRepository.save(managedUser);

        return appUtilsComment.commentToDto(savedComment);
    }

    @Override
    public String softDeleteComment(int id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
        comment.setDeleted(true);
        commentRepository.save(comment);
        return id + ": soft deleted Successfully";
    }

    @Override
    public String hardDeleteComment(int id) throws ResourceNotFoundException {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
        commentRepository.deleteById(id);

        return id + ": hard deleted Successfully";
    }

    @Override
    public String restoreComment(int id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
        comment.setDeleted(false);
        commentRepository.save(comment);
        return id + " is restored successfully";
    }

    @Override
    public String getUserNameByComment(int id) {

        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));

        if (comment != null && comment.getPost() != null && comment.getUser() != null) {
            return comment.getUser().getUsername();
        } else {
            return "No user found for the given comment.";
        }
    }

    @Override
    public List<CommentDTO> getCommentsByPost(int postId, int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Comment> commentLists = commentRepository.findCommentByPostPostId(postId,pageable);

        if(commentLists.isEmpty()){
            throw new NoDataFoundException("No Comment Found in given post id: " + postId);
        }
        return appUtilsComment.commentsToDtos(commentLists.getContent());
    }

    @Override
    public List<CommentDTO> getCommentsByUser(int userId, int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Comment> commentLists = commentRepository.findCommentByUserUserId(userId,pageable);

        if(commentLists.isEmpty()){
            throw new NoDataFoundException("No comment found for given user id: " + userId);
        }
        return appUtilsComment.commentsToDtos(commentLists.getContent());
    }


}