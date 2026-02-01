package com.example.blogplatform.service;

import com.example.blogplatform.model.PostStatus;
import com.example.blogplatform.model.dto.*;
import com.example.blogplatform.model.entity.AppUser;
import com.example.blogplatform.model.entity.Category;
import com.example.blogplatform.model.entity.Comment;
import com.example.blogplatform.model.entity.Post;
import com.example.blogplatform.repository.AppUserRepository;
import com.example.blogplatform.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
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
    @Transactional(readOnly = true)
    public List<Post> getDrafts(AppUser author, Long categoryId) {
        if(categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            return postRepository.findAllByAuthorAndStatusAndCategory(
                    author,
                    PostStatus.DRAFT,
                    category
            );

        }
        return postRepository.findAllByAuthorAndStatus(author,PostStatus.DRAFT);
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findByIdAndStatus(id,PostStatus.PUBLISHED)
                .orElseThrow(()->new EntityNotFoundException("Post does not exist with id: " + id));
    }

    @Override
    public void deletePost(Long id) {
        Post post = getPostById(id);
        postRepository.delete(post);
    }

    @Override
    @Transactional
    public Post createPost(AppUser user, CreatePostRequestDTO createPostRequestDTO) {
        if (user == null) {
            throw new IllegalStateException("Authenticated user is missing");
        }

        Post newPost = Post.builder()
                .title(createPostRequestDTO.getTitle())
                .content(createPostRequestDTO.getContent())
                .status(createPostRequestDTO.getStatus() != null
                        ? createPostRequestDTO.getStatus()
                        : PostStatus.DRAFT)
                .author(user)
                .category(categoryService.getCategoryById(createPostRequestDTO.getCategoryId()))
                .comments(new HashSet<>())
                .build();
        return postRepository.save(newPost);
    }

    @Override
    @Transactional
    public Post updatePost(Long id, UpdatePostRequestDTO updatePostRequestDTO) {
        Post postToUpdate = postRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Post does not exist with id: " + id));
        postToUpdate.setTitle(updatePostRequestDTO.getTitle());
        postToUpdate.setContent(updatePostRequestDTO.getContent());
        postToUpdate.setStatus(updatePostRequestDTO.getStatus());
        Long updatePostRequestCategoryId = updatePostRequestDTO.getCategoryId();
        if(!updatePostRequestCategoryId.equals(postToUpdate.getCategory().getId())) {
            Category updatedCategory = categoryService.getCategoryById(updatePostRequestCategoryId);
            postToUpdate.setCategory(updatedCategory);
        }
        return postRepository.save(postToUpdate);
    }

    @Override
    @Transactional
    public PostDTO publishDraft(Long id, AppUser user) {
        Post postToPublish = postRepository.findByIdAndStatus(id,PostStatus.DRAFT)
                .orElseThrow(()->new EntityNotFoundException("Draft does not exist with id: " + id));
        if(!user.equals(postToPublish.getAuthor())){
            throw new AccessDeniedException("You can not see/publish other users' drafts.");
        }
        postToPublish.setStatus(PostStatus.PUBLISHED);
        return  toPostDTO(postRepository.save(postToPublish));
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
                .postStatus(post.getStatus())
                .comments(post.getComments().stream().map(Comment::getContent).collect(Collectors.toSet()))
                .build();

    }
}
