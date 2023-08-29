package com.vaistra.controller;

import com.vaistra.dto.CommentDTO;
import com.vaistra.entities.Comment;
import com.vaistra.exception.NoDataFoundException;
import com.vaistra.repositories.CommentRepository;
import com.vaistra.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comment")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComment(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                          @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                          @RequestParam(value = "sortBy", defaultValue = "commentId", required = false) String sortBy,
                                                          @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {
        return new ResponseEntity<>(commentService.getAllComment(pageNo, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }


    @PostMapping("/add")
    public ResponseEntity<CommentDTO> addComment(@RequestBody @Valid CommentDTO commentDTO){
        return new ResponseEntity<>(commentService.addComment(commentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable int id) {
        return new ResponseEntity<>(commentService.getCommentById(id), HttpStatus.OK);
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<CommentDTO> updateComment(@Valid @RequestBody CommentDTO commentDTO, @PathVariable int id) {
        return new ResponseEntity<>(commentService.updateComment(commentDTO, id), HttpStatus.OK);
    }

    @PutMapping("/softdelete/id/{id}")
    public ResponseEntity<String> softDeleteComment(@PathVariable int id) {
        return new ResponseEntity<>(commentService.softDeleteComment(id), HttpStatus.OK);
    }

    @PutMapping("/restorecomment/id/{id}")
    public ResponseEntity<String> restoreComment(@PathVariable int id) {
        return new ResponseEntity<>(commentService.restoreComment(id), HttpStatus.OK);
    }

    @DeleteMapping("/harddelete/id/{id}")
    public ResponseEntity<String> hardDeleteState(@PathVariable int id) {
        return new ResponseEntity<>(commentService.hardDeleteComment(id), HttpStatus.OK);
    }

    @GetMapping("/commentbypost/id/{id}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPost(@PathVariable int id, @RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                              @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                              @RequestParam(value = "sortBy", defaultValue = "commentId", required = false) String sortBy,
                                                              @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection){
        return new ResponseEntity<>(commentService.getCommentsByPost(id,pageNo,pageSize,sortBy,sortDirection),HttpStatus.OK);
    }

    @GetMapping("/commentbyuser/id/{id}")
    public ResponseEntity<List<CommentDTO>> getCommentsByUser(@PathVariable int id, @RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                              @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                              @RequestParam(value = "sortBy", defaultValue = "commentId", required = false) String sortBy,
                                                              @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection){
        return new ResponseEntity<>(commentService.getCommentsByUser(id,pageNo,pageSize,sortBy,sortDirection),HttpStatus.OK);

    }
}