package com.example.blogplatform.repository;

import com.example.blogplatform.model.PostStatus;
import com.example.blogplatform.model.entity.AppUser;
import com.example.blogplatform.model.entity.Category;
import com.example.blogplatform.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllByStatusAndCategory(PostStatus status, Category category);
    List<Post> findAllByStatus(PostStatus postStatus);
    List<Post> findAllByAuthorAndStatusAndCategory(AppUser author, PostStatus postStatus, Category category);
    List<Post> findAllByAuthorAndStatus(AppUser author,PostStatus postStatus);



}
