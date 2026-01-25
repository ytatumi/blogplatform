package com.example.blogplatform.controller;

import com.example.blogplatform.config.CustomUserDetailService;
import com.example.blogplatform.model.dto.CreatePostRequestDTO;
import com.example.blogplatform.model.dto.PostDTO;
import com.example.blogplatform.model.dto.UpdatePostRequestDTO;
import com.example.blogplatform.model.entity.AppUser;
import com.example.blogplatform.model.entity.Post;
import com.example.blogplatform.service.PostService;
import com.example.blogplatform.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final CustomUserDetailService customUserDetailService;


    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts(
            @RequestParam(required=false) Long categoryId) {
       List<Post> posts =  postService.getAllPosts(categoryId);
       List<PostDTO> postDTOs = posts.stream().map(postService::toPostDTO).toList();
       return ResponseEntity.ok(postDTOs);

    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(
            @Valid @RequestBody CreatePostRequestDTO createPostRequestDTO,
            @AuthenticationPrincipal String loggedInUser
    ){

        AppUser user= userService.getUserByUsername (loggedInUser);
        Post createdPost = postService.createPost(user, createPostRequestDTO);
        PostDTO createdPostDTO = postService.toPostDTO(createdPost);
        return new ResponseEntity<>(createdPostDTO, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost
            (@PathVariable Long id, @Valid @RequestBody UpdatePostRequestDTO updatePostRequestDTO){
        Post updatedPost = postService.updatePost(id, updatePostRequestDTO);
        PostDTO updatedPostDTO =postService.toPostDTO(updatedPost);
        return ResponseEntity.ok(updatedPostDTO);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id){
        Post post = postService.getPostById(id);
        PostDTO postDTO = postService.toPostDTO(post);
        return ResponseEntity.ok(postDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();

    }


}
