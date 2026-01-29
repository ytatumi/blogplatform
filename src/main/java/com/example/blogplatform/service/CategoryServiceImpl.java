package com.example.blogplatform.service;

import com.example.blogplatform.model.dto.CategoryDTO;
import com.example.blogplatform.model.dto.CreateCategoryRequestDTO;
import com.example.blogplatform.model.entity.Category;
import com.example.blogplatform.repository.CategoryRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAllWithPosts();
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        String categoryName= category.getName();
        if(categoryRepository.existsByNameIgnoreCase(categoryName)){
            throw new EntityExistsException("Category already exists " + categoryName);
        }
        return categoryRepository.save(category);

        }

    @Override
    public void deleteCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            if(category.get().getPosts().size() > 0){
                throw new EntityExistsException("Category has posts");
            }
            categoryRepository.deleteById(id);
        }
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).
                orElseThrow(()->new EntityNotFoundException("Category not found with id " + id));
    }

    @Override
    public CategoryDTO toCategoryDTO(Category category){
        return CategoryDTO.builder()
                .id(category.getId())
                .categoryName(category.getName()).build();
    }

    @Override
    public Category toEntity(CreateCategoryRequestDTO createCategoryRequestDTO) {
        return Category.builder()
                .name(createCategoryRequestDTO.getCategoryName())
                .build();
    }


}
