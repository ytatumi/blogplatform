package com.example.blogplatform.controller;

import com.example.blogplatform.model.dto.PostDTO;
import com.example.blogplatform.model.entity.Post;
import com.example.blogplatform.service.PostService;
import com.example.blogplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts(
            @RequestParam(required=false) Long categoryId) {
       List<Post> posts =  postService.getAllPosts(categoryId);
       List<PostDTO> postDTOs = posts.stream().map(postService::toPostDTO).toList();
       return ResponseEntity.ok(postDTOs);

    }



}
