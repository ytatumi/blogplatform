package com.example.blogplatform.repository;

import com.example.blogplatform.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c from Category c LEFT JOIN c.posts")
    List<Category>findAllWithPosts();
    boolean existsByNameIgnoreCase(String Name);

}
