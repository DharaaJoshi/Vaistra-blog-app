package com.vaistra.services.impl;

import com.vaistra.dto.PostDTO;
import com.vaistra.dto.TagDTO;
import com.vaistra.entities.Category;
import com.vaistra.entities.Post;
import com.vaistra.entities.Tag;
import com.vaistra.entities.User;
import com.vaistra.exception.IsActiveExceptionHandler;
import com.vaistra.exception.IsDeleteExceptionHandler;
import com.vaistra.exception.ResourceNotFoundException;
import com.vaistra.repositories.*;
import com.vaistra.services.PostService;
import com.vaistra.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;
    private final AppUtils appUtils;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, CategoryRepository categoryRepository,
                           CommentRepository commentRepository, TagRepository tagRepository, AppUtils appUtils)
    {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.commentRepository = commentRepository;
        this.tagRepository = tagRepository;
        this.appUtils = appUtils;
    }

    @Override
    public PostDTO addPost(PostDTO postDTO) {

        // USER VALIDATION
        User user = userRepository.findById(postDTO.getUser().getUserId())
                .orElseThrow(()->new ResourceNotFoundException("User with id "+postDTO.getUser().getUserId()+" not found!" ));
        if(!user.isActive())
            throw new IsActiveExceptionHandler("User with id "+user.getUserId()+" is inactive!");
        if(user.isDeleted())
            throw new IsDeleteExceptionHandler("User with id "+user.getUserId()+" is deleted!");

        // CATEGORY VALIDATION
        Category category = categoryRepository.findById(postDTO.getCategory().getCategoryId())
                .orElseThrow(()->new ResourceNotFoundException("Category with id "+postDTO.getCategory().getCategoryId()+" not found!" ));
        if(!category.isActive())
            throw new IsActiveExceptionHandler("Category with id "+category.getCategoryId()+" is inactive!");
        if(category.isDeleted())
            throw new IsActiveExceptionHandler("Category with id "+category.getCategoryId()+" is deleted!");

        Post post = new Post();
        post.setPostContent(postDTO.getPostContent());
        post.setPostTitle(postDTO.getPostTitle());
        post.setUser(user);
        post.setCategory(category);
        post.setCreatedAt(LocalDateTime.now());
        post.setActive(true);

        for(TagDTO dto : postDTO.getTags())
        {
            if (tagRepository.existsByTagName(dto.getTagName()))
            {
                Tag t = tagRepository.findByTagName(dto.getTagName());
                post.getTags().add(t);
            }
            else
                post.getTags().add(appUtils.dtoToTag(dto));
        }

        Post savedPost = postRepository.save(post);

        for(Tag tag : savedPost.getTags())
        {
            tag.getPosts().add(savedPost);
            tagRepository.save(tag);
        }

        User managedUser = userRepository.save(user);
        Category managedCategory = categoryRepository.save(category);

        managedUser.getPosts().add(savedPost);
        managedCategory.getPosts().add(savedPost);

        userRepository.save(managedUser);
        categoryRepository.save(managedCategory);

        return appUtils.postToDto(savedPost);
    }

    @Override
    public PostDTO getPostById(Integer postId) {
        return appUtils.postToDto(postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post with id "+postId+" not found")));
    }

    @Override
    public List<PostDTO> getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = postRepository.findAll(pageable);
        return appUtils.postsToDtos(pagePost.getContent());
    }

    @Override
    public List<PostDTO> getAllPostsByDeleted(boolean deleted, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = postRepository.findAllByIsDeleted(deleted, pageable);
        return appUtils.postsToDtos(pagePost.getContent());
    }

    @Override
    public List<PostDTO> getPostsByUserId(Integer userId, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User with id "+userId+" not found!"));

        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = postRepository.findAllByUser(user, pageable);
        return appUtils.postsToDtos(pagePost.getContent());
    }

    @Override
    public List<PostDTO> getPostsByCommentId(Integer commentId, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        return null;
    }

    @Override
    public List<PostDTO> getPostsByCategoryId(Integer categoryId, int pageNumber, int pageSize, String sortBy, String sortDirection) {

        Category category =categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category with id "+categoryId+" not found!"));

        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = postRepository.findAllByCategory(category, pageable);
        return appUtils.postsToDtos(pagePost.getContent());
    }

    @Override
    public PostDTO updatePost(Integer postId, PostDTO postDTO) {

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post with id "+postId+" not found!"));
        if(!post.isActive())
            throw new IsActiveExceptionHandler("Post with id "+postId+" is inactive!");
        if(post.isDeleted())
            throw new IsDeleteExceptionHandler("Post with id "+postId+" is deleted!");

        post.setPostTitle(postDTO.getPostTitle());
        post.setPostContent(postDTO.getPostContent());
        if(postDTO.getCategory() != null)
        {
            Category category = categoryRepository.findById(postDTO.getCategory().getCategoryId())
                    .orElseThrow(()->new ResourceNotFoundException("Category with id "+postDTO.getCategory().getCategoryId()+" not found!"));
            System.out.println(category);
            post.setCategory(category);
        }

        for(TagDTO dto : postDTO.getTags())
        {
            if (tagRepository.existsByTagName(dto.getTagName()))
            {
                Tag t = tagRepository.findByTagName(dto.getTagName());
                post.getTags().add(t);
            }
            else
                post.getTags().add(appUtils.dtoToTag(dto));
        }

        return appUtils.postToDto(postRepository.save(post));
    }

    @Override
    public String softDeletePost(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post with id "+postId+" not found!"));
        if(post.isDeleted())
            return "Post with id "+postId+" is already deleted";

        post.setDeleted(true);
        postRepository.save(post);
        return "Post with id "+postId+" soft deleted!";
    }

    @Override
    public String hardDeletePost(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post with id "+postId+" not found!"));
        postRepository.delete(post);
        return "Post with id "+postId+" hard deleted!";
    }

    @Override
    public String restorePost(Integer postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post with id "+postId+" not found!"));
        if(!post.isDeleted())
            return "Post with id "+postId+" is already present!";

        post.setDeleted(false);
        postRepository.save(post);
        return "Post with id "+postId+" restored!";
    }
}
