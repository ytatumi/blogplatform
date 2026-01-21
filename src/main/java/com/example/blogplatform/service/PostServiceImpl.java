package com.example.blogplatform.service;

import com.example.blogplatform.model.PostStatus;
import com.example.blogplatform.model.dto.AuthorDTO;
import com.example.blogplatform.model.dto.CategoryDTO;
import com.example.blogplatform.model.dto.PostDTO;
import com.example.blogplatform.model.entity.Category;
import com.example.blogplatform.model.entity.Post;
import com.example.blogplatform.repository.CategoryRepository;
import com.example.blogplatform.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    private final CategoryService categoryService;


    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPosts(Long categoryId) {
        if(categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            return postRepository.findAllByStatusAndCategory(
                    PostStatus.PUBLISHED,
                    category
            );

        }
        return postRepository.findAllByStatus(PostStatus.PUBLISHED);
    }

    @Override
    public PostDTO toPostDTO(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(new AuthorDTO(post.getAuthor().getId(), post.getAuthor().getName()))
                .category(new CategoryDTO(post.getCategory().getId(), post.getCategory().getName()))
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .postStatus(post.getStatus()).build();

    }
}
