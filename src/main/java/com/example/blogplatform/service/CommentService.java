package com.example.blogplatform.service;

import com.example.blogplatform.model.dto.CommentDTO;
import com.example.blogplatform.model.dto.CommentRequestDTO;
import com.example.blogplatform.model.entity.Comment;

public interface CommentService {
    CommentDTO createComment(CommentRequestDTO commentRequestDTO);
    public CommentDTO toCommentDTO (Comment comment);
}
