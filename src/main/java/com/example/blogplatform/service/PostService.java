package com.example.blogplatform.service;

import com.example.blogplatform.model.dto.CreatePostRequestDTO;
import com.example.blogplatform.model.dto.PostDTO;
import com.example.blogplatform.model.dto.UpdatePostRequestDTO;
import com.example.blogplatform.model.entity.AppUser;
import com.example.blogplatform.model.entity.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts(Long categoryId);
    Post getPostById(Long id);
    void deletePost(Long id);
    Post createPost(AppUser user, CreatePostRequestDTO createPostRequestDTO);
    Post updatePost(Long id, UpdatePostRequestDTO updatePostRequestDTO);
    PostDTO toPostDTO(Post post);
}
