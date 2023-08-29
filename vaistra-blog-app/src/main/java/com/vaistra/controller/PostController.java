package com.vaistra.controller;

import com.vaistra.dto.PostDTO;
import com.vaistra.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("post")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService)
    {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDTO> addPost(@Valid @RequestBody PostDTO postDTO)
    {
        return new ResponseEntity<>(postService.addPost(postDTO), HttpStatus.CREATED);
    }

    @GetMapping("{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Integer postId)
    {
        return new ResponseEntity<>(postService.getPostById(postId), HttpStatus.FOUND);
    }
    @GetMapping("all")
    public ResponseEntity<List<PostDTO>> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                     @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                     @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
                                                     @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection)
    {
        return new ResponseEntity<>(postService.getAllPosts(pageNumber, pageSize, sortBy, sortDirection), HttpStatus.FOUND);
    }
    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPostsByDeleted(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                              @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                              @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
                                                              @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection)
    {
        return new ResponseEntity<>(postService.getAllPostsByDeleted(false, pageNumber, pageSize, sortBy, sortDirection), HttpStatus.FOUND);
    }

    @GetMapping("userId/{userId}")
    public ResponseEntity<List<PostDTO>> getPostByUserId(@PathVariable Integer userId,@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                         @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                         @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
                                                         @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection)
    {
        return new ResponseEntity<>(postService.getPostsByUserId(userId, pageNumber, pageSize, sortBy,sortDirection ), HttpStatus.FOUND);
    }

    @GetMapping("categoryId/{categoryId}")
    public ResponseEntity<List<PostDTO>> getPostsByCategoryId(@PathVariable Integer categoryId,@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                              @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                              @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
                                                              @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection)
    {
        return new ResponseEntity<>(postService.getPostsByCategoryId(categoryId, pageNumber, pageSize, sortBy, sortDirection), HttpStatus.FOUND);
    }

    @PutMapping("{postId}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable Integer postId)
    {
        return new ResponseEntity<>(postService.updatePost(postId, postDTO), HttpStatus.OK);
    }

    @PutMapping("softDelete/{postId}")
    public ResponseEntity<String> softDeletePost(@PathVariable Integer postId)
    {
        return new ResponseEntity<>(postService.softDeletePost(postId), HttpStatus.OK);
    }
    @DeleteMapping("hardDelete/{postId}")
    public ResponseEntity<String> hardDeletePost(@PathVariable Integer postId)
    {
        return new ResponseEntity<>(postService.hardDeletePost(postId), HttpStatus.OK);
    }

    @PutMapping("restore/{postId}")
    public ResponseEntity<String> restorePost(@PathVariable Integer postId)
    {
        return new ResponseEntity<>(postService.restorePost(postId), HttpStatus.OK);
    }
}
