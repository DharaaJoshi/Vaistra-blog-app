package com.vaistra.services.impl;

import com.vaistra.dto.CategoryDTO;
import com.vaistra.entities.Category;
import com.vaistra.entities.Tag;
import com.vaistra.exception.DuplicateEntryException;
import com.vaistra.exception.ResourceNotFoundException;
import com.vaistra.repositories.CategoryRepository;
import com.vaistra.services.CategoryService;
import com.vaistra.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    public CategoryServiceImpl(AppUtils appUtils) {
        this.appUtils = appUtils;
    }

    private final AppUtils appUtils;
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {

        Category c = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());

        if (c != null)
            throw new DuplicateEntryException("Category name: " + c.getCategoryName() + " already exist..!");

        categoryDTO.setCategoryName(categoryDTO.getCategoryName().toUpperCase());
        categoryDTO.setCategoryDescription(categoryDTO.getCategoryDescription());

        return appUtils.categoryToDto(categoryRepository.save(appUtils.dtoToCategory(categoryDTO)));

    }

    @Override
    public CategoryDTO getCategoryById(int id) {
        Category c = categoryRepository.findByIdNotTrashed(id);
        if (c == null)
            throw new ResourceNotFoundException("Tag with id '" + id + "' Not Found!");
        else
            return appUtils.categoryToDto(c);
    }

    @Override
    public CategoryDTO getCategoryByIdTrashed(int id) {

        Category c = categoryRepository.findByIdTrashed(id);
        if (c == null)
            throw new ResourceNotFoundException("Category with id '" + id + "' Not Found!");
        else
            return appUtils.categoryToDto(c);
    }

    @Override
    public CategoryDTO getCategoryByIdInActive(int id) {
        Category c = categoryRepository.findByIdInActive(id);
        if (c == null)
            throw new ResourceNotFoundException("Category with id '" + id + "' Not Found!");
        else
            return appUtils.categoryToDto(c);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<CategoryDTO> CDto = new ArrayList<>();

        List<Category> categories = categoryRepository.findAll();

        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("No Records Found..!");
        } else {
            return appUtils.categoriesToDtos(categories);
        }
    }

    @Override
    public List<CategoryDTO> getAllCategories(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
        List<CategoryDTO> CDto = new ArrayList<>();

        Sort s = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNo, pageSize, s);

        Page<Category> categories = categoryRepository.findAll(p);
        List<Category> content = categories.getContent();

//      List<Country> countries = findAllFilter(isDeleted);

        if (content.isEmpty()) {
            throw new ResourceNotFoundException("No Records Found..!");
        } else {

            for (Category category : content) {
                CategoryDTO c = appUtils.categoryToDto(category);
//                System.out.println(c.toString());
                CDto.add(c);
            }
            return CDto;
        }
    }

    @Override
    public List<CategoryDTO> getAllCategoriesSortBy(String field) {
        return null;
    }

    @Override
    public List<CategoryDTO> getAllTrashedCategories() {
        List<CategoryDTO> CDto = new ArrayList<>();

        List<Category> categories = categoryRepository.findAllTrashed();

        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("No Records Found..!");
        } else {
            return appUtils.categoriesToDtos(categories);
        }
    }

    @Override
    public List<CategoryDTO> getAllInActiveCategories() {
        List<CategoryDTO> CDto = new ArrayList<>();

        List<Category> categories = categoryRepository.findAllInActive();

        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("No Records Found..!");
        } else {
            return appUtils.categoriesToDtos(categories);
        }
    }

    @Override
    public CategoryDTO updateCategoryById(int id, CategoryDTO categoryDTO) {
        Category c;

        c = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category with Id '" + id + "' not found!"));

        if (categoryDTO.getCategoryName() != null) {
            if (categoryRepository.findByCategoryName(categoryDTO.getCategoryName()) != null)
                throw new DuplicateEntryException("Country name: " + categoryDTO.getCategoryName() + " already exist..!");
            else
                c.setCategoryName(categoryDTO.getCategoryName().toUpperCase());
        }
        c.setCategoryDescription(categoryDTO.getCategoryDescription());
        c.setActive(categoryDTO.isActive());

        categoryRepository.save(c);
        return appUtils.categoryToDto(c);
    }

    @Override
    public CategoryDTO updateStatusById(int id, boolean status) {
        return null;
    }

    @Override
    public int deleteCategoryById(int id) {
        Category c = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category with Id '" + id + "' not found!"));
        if (c != null) {
            categoryRepository.delete(c);
            return 1;
        } else
            return 0;
    }

    @Override
    public int trashCategoryById(int id) {
//        Category c = categoryRepository.findById(id).get();
        Category c = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category with Id '" + id + "' not found!"));
        if (c != null) {
            c.setActive(Boolean.FALSE);
            c.setDeleted(Boolean.TRUE);
            c.setDeletedAt(LocalDateTime.now());
            categoryRepository.save(c);
            return 1;
        } else
            return 0;
    }

    @Override
    public int restoreCategoryById(int id) {
        Category c = categoryRepository.findByIdTrashed(id);

        if (c != null) {
            c.setActive(Boolean.TRUE);
            c.setDeleted(Boolean.FALSE);
            c.setDeletedAt(null);
            categoryRepository.save(c);
            return 1;
        } else
            throw new ResourceNotFoundException("Country with Id '" + id + "' not found!");
    }

}
