package com.example.blogplatform.service;

import com.example.blogplatform.model.dto.PostDTO;
import com.example.blogplatform.model.entity.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts(Long categoryId);
    PostDTO toPostDTO(Post post);
}
