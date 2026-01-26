package com.example.blogplatform.service;

import com.example.blogplatform.model.dto.CategoryDTO;
import com.example.blogplatform.model.dto.CreateCategoryRequestDTO;
import com.example.blogplatform.model.entity.Category;

import java.util.List;



public interface CategoryService {
    List<Category> getAllCategories();
    Category createCategory(Category category);
    void deleteCategory(Long id);
    Category getCategoryById(Long id);
    CategoryDTO toCategoryDTO(Category category);
    Category  toEntity(CreateCategoryRequestDTO createCategoryRequestDTO);
}
