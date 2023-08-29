package com.vaistra.controller;

import com.vaistra.dto.CategoryDTO;
import com.vaistra.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("add")
    public ResponseEntity<String> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO c = categoryService.addCategory(categoryDTO);
        if (c != null)
            return new ResponseEntity<>("Category created...", HttpStatus.OK);
        else
            return new ResponseEntity<>("Category not created...!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("all")
//    public ResponseEntity<List<CategoryDTO>> getAllCategories() {

    public ResponseEntity<List<CategoryDTO>> getAllCategories
            (@RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
             @RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize,
             @RequestParam(value = "sortBy", defaultValue = "categoryName", required = false) String sortBy,
             @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {

//        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
        return new ResponseEntity<>(categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }

    @GetMapping("{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer categoryId) {
        return new ResponseEntity<>(categoryService.getCategoryById(categoryId), HttpStatus.OK);
    }

    @GetMapping("trashed/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryByIdTrashed(@PathVariable Integer categoryId) {
        return new ResponseEntity<>(categoryService.getCategoryByIdTrashed(categoryId), HttpStatus.OK);
    }

    @GetMapping("trashed")
//    public ResponseEntity<List<CategoryDTO>> getAllTrashedCategories(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
//                                                                     @RequestParam(value = "pageSize", defaultValue = "2", required = false) Integer pageSize,
//                                                                     @RequestParam(value = "sortBy", defaultValue = "categoryName", required = false) String sortBy,
//                                                                     @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {
    public ResponseEntity<List<CategoryDTO>> getAllTrashedCategories() {
        return new ResponseEntity<>(categoryService.getAllTrashedCategories(), HttpStatus.OK);
    }

    @GetMapping("inactive/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryByIdInActive(@PathVariable Integer categoryId) {
        return new ResponseEntity<>(categoryService.getCategoryByIdInActive(categoryId), HttpStatus.OK);
    }

    @GetMapping("inactive")
//    public ResponseEntity<List<CategoryDTO>> getAllInActiveCategories(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
//                                                                     @RequestParam(value = "pageSize", defaultValue = "2", required = false) Integer pageSize,
//                                                                     @RequestParam(value = "sortBy", defaultValue = "categoryName", required = false) String sortBy,
//                                                                     @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {
    public ResponseEntity<List<CategoryDTO>> getAllInActiveCategories() {
        return new ResponseEntity<>(categoryService.getAllInActiveCategories(), HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateCategoryById(@PathVariable Integer id, @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO c = categoryService.updateCategoryById(id, categoryDTO);

        if (c != null)
            return new ResponseEntity<>("Category is updated...", HttpStatus.OK);
        else
            return new ResponseEntity<>("Category not updated...!", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Integer id) {
        int flag = categoryService.deleteCategoryById(id);

        if (flag == 1)
            return new ResponseEntity<>("Category with ID " + id + " is deleted...", HttpStatus.OK);
        else
            return new ResponseEntity<>("Category with ID " + id + " is not deleted...!", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("trash/{id}")
    public ResponseEntity<String> trashCategoryById(@PathVariable Integer id) {
        int flag = categoryService.trashCategoryById(id);

        if (flag == 1)
            return new ResponseEntity<>("Category with ID " + id + " is Trashed...", HttpStatus.OK);
        else
            return new ResponseEntity<>("Category with ID " + id + " is not Trashed...!", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("restore/{id}")
    public ResponseEntity<String> restoreCategoryById(@PathVariable Integer id) {
        int flag = categoryService.restoreCategoryById(id);

        if (flag == 1)
            return new ResponseEntity<>("Category with ID " + id + " is Restored...", HttpStatus.OK);
        else
            return new ResponseEntity<>("Category with ID " + id + " is not Restored...!", HttpStatus.BAD_REQUEST);
    }
}
