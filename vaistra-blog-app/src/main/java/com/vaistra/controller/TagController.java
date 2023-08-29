package com.vaistra.controller;

import com.vaistra.dto.TagDTO;
import com.vaistra.services.TagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tag")
public class TagController {

    @Autowired
    TagService tagService;

    @PostMapping("add")
    public ResponseEntity<String> addTag(@Valid @RequestBody TagDTO tagDTO) {
        TagDTO c = tagService.addTag(tagDTO);
        if (c != null)
            return new ResponseEntity<>("Tag created...", HttpStatus.OK);
        else
            return new ResponseEntity<>("Tag not created...!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("all")
    public ResponseEntity<List<TagDTO>> getAllTags() {

//    public ResponseEntity<List<TagDTO>> getAllTags
//            (@RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
//             @RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize,
//             @RequestParam(value = "sortBy", defaultValue = "tagName", required = false) String sortBy,
//             @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {

        return new ResponseEntity<>(tagService.getAllTags(), HttpStatus.OK);
//        return new ResponseEntity<>(tagService.getAllTags(pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }

    @GetMapping("{tagId}")
    public ResponseEntity<TagDTO> getTagById(@PathVariable Integer tagId) {
        return new ResponseEntity<>(tagService.getTagById(tagId), HttpStatus.OK);
    }

    @GetMapping("trashed/{tagId}")
    public ResponseEntity<TagDTO> getTagByIdTrashed(@PathVariable Integer tagId) {
        return new ResponseEntity<>(tagService.getTagByIdTrashed(tagId), HttpStatus.OK);
    }

    @GetMapping("trashed")
//    public ResponseEntity<List<TagDTO>> getAllTrashedTags(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
//                                                                     @RequestParam(value = "pageSize", defaultValue = "2", required = false) Integer pageSize,
//                                                                     @RequestParam(value = "sortBy", defaultValue = "tagName", required = false) String sortBy,
//                                                                     @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {
    public ResponseEntity<List<TagDTO>> getAllTrashedTags() {
        return new ResponseEntity<>(tagService.getAllTrashedTags(), HttpStatus.OK);
    }

    @GetMapping("inactive/{tagId}")
    public ResponseEntity<TagDTO> getTagByIdInActive(@PathVariable Integer tagId) {
        return new ResponseEntity<>(tagService.getTagByIdInActive(tagId), HttpStatus.OK);
    }

    @GetMapping("inactive")
//    public ResponseEntity<List<CategoryDTO>> getAllInActiveTags(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
//                                                                     @RequestParam(value = "pageSize", defaultValue = "2", required = false) Integer pageSize,
//                                                                     @RequestParam(value = "sortBy", defaultValue = "tagName", required = false) String sortBy,
//                                                                     @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {
    public ResponseEntity<List<TagDTO>> getAllInActiveTags() {
        return new ResponseEntity<>(tagService.getAllInActiveTags(), HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateTagById(@PathVariable Integer id, @RequestBody TagDTO tagDTO) {
        TagDTO t = tagService.updateTagById(id, tagDTO);

        if (t != null)
            return new ResponseEntity<>("Tag is updated...", HttpStatus.OK);
        else
            return new ResponseEntity<>("Tag not updated...!", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteTagById(@PathVariable Integer id) {
        int flag = tagService.deleteTagById(id);

        if (flag == 1)
            return new ResponseEntity<>("Tag with ID " + id + " is deleted...", HttpStatus.OK);
        else
            return new ResponseEntity<>("Tag with ID " + id + " is not deleted...!", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("trash/{id}")
    public ResponseEntity<String> trashTagById(@PathVariable Integer id) {
        int flag = tagService.trashTagById(id);

        if (flag == 1)
            return new ResponseEntity<>("Tag with ID " + id + " is Trashed...", HttpStatus.OK);
        else
            return new ResponseEntity<>("Tag with ID " + id + " is not Trashed...!", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("restore/{id}")
    public ResponseEntity<String> restoreTagById(@PathVariable Integer id) {
        int flag = tagService.restoreTagById(id);

        if (flag == 1)
            return new ResponseEntity<>("Tag with ID " + id + " is Restored...", HttpStatus.OK);
        else
            return new ResponseEntity<>("Tag with ID " + id + " is not Restored...!", HttpStatus.BAD_REQUEST);
    }
}
