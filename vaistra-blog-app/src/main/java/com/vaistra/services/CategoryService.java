package com.vaistra.services;

import com.vaistra.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    public CategoryDTO addCategory(CategoryDTO categoryDTO);

    public CategoryDTO getCategoryById(int id);

    public CategoryDTO getCategoryByIdTrashed(int id);

    public CategoryDTO getCategoryByIdInActive(int id);

    public List<CategoryDTO> getAllCategories(Integer pageNo, Integer pageSize, String sortBy, String sortOrder);

    public List<CategoryDTO> getAllCategories();

    public List<CategoryDTO> getAllCategoriesSortBy(String field);

    public List<CategoryDTO> getAllTrashedCategories();

    public List<CategoryDTO> getAllInActiveCategories();

    public CategoryDTO updateCategoryById(int id, CategoryDTO categoryDTO);

    CategoryDTO updateStatusById(int id, boolean status);

    public int deleteCategoryById(int id);

    public int trashCategoryById(int id);

    public int restoreCategoryById(int id);

}
