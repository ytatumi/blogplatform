package com.example.blogplatform.controller;

import com.example.blogplatform.model.dto.CommentDTO;
import com.example.blogplatform.model.dto.CommentRequestDTO;
import com.example.blogplatform.model.entity.Comment;
import com.example.blogplatform.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDTO> postComment(@Valid @RequestBody CommentRequestDTO commentRequestDTO) {
        CommentDTO commentDTO = commentService.createComment(commentRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDTO);
    }


}
