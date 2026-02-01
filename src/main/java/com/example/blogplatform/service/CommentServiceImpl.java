package com.example.blogplatform.service;

import com.example.blogplatform.config.CustomUserDetailService;
import com.example.blogplatform.model.dto.CommentDTO;
import com.example.blogplatform.model.dto.CommentRequestDTO;
import com.example.blogplatform.model.entity.AppUser;
import com.example.blogplatform.model.entity.Comment;
import com.example.blogplatform.model.entity.Post;
import com.example.blogplatform.repository.AppUserRepository;
import com.example.blogplatform.repository.CommentRepository;
import com.example.blogplatform.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final AppUserRepository AppUserRepository;
    private final CustomUserDetailService customUserDetailService;


    @Override
    @Transactional
    public CommentDTO createComment(CommentRequestDTO commentRequestDTO) {
        Post post = postRepository.findById(commentRequestDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post Not Found"));

        AppUser currentUser= AppUserRepository.findByUsername(customUserDetailService.extractCurrentUsername())
                .orElseThrow(() -> new IllegalArgumentException("User Not Registered"));

        Comment newComment = Comment.builder()
                .post(post)
                .user(currentUser)
                .content(commentRequestDTO.getComment())
                .build();
        return toCommentDTO(commentRepository.save(newComment));

    }

    @Override
    public CommentDTO toCommentDTO(Comment comment) {
        return  CommentDTO.builder()
                .postId(comment.getPost().getId())
                .comment(comment.getContent())
                .user(comment.getUser().getUsername())
                .createdAt(comment.getCreatedAt())
                .build();
    }


}
