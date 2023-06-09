package com.example.twm.service.impl;

import com.example.twm.domain.User;
import com.example.twm.domain.post.Post;
import com.example.twm.domain.post.PostComments;
import com.example.twm.jwt.AuthService;
import com.example.twm.repos.CommentRepo;
import com.example.twm.repos.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepo postRepo;
    private final CommentRepo commentRepo;
    private final AuthService authService;
    private final UserService userService;

    private final Path root = Paths.get("uploads\\posts photos");

    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public void createPost(String text, MultipartFile file) {
        init();
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
            System.out.println(file.getOriginalFilename());
            System.out.println(text);
            User user = userService.findById(authService.getAuthInfo().getId()).get();
            postRepo.save(Post
                    .builder()
                    .text(text)
                    .photo(file.getOriginalFilename())
                    .author(user)
                    .timestamp(new Date())
                    .build());
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }

    public Resource getPostPhoto(Long id) {
        try {
            Path file = root.resolve(postRepo.findById(id).get().getPhoto());
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public List<Post> getPosts(Long count) {
        return postRepo.findPostsByRowNumRange(count - 10, count);
    }

    public void setComment(String text, Long postId) {
        commentRepo.save(PostComments
                .builder()
                        .post(postRepo.findById(postId).get())
                        .user(userService.findById(authService.getAuthInfo().getId()).get())
                        .timestamp(new Date())
                        .text(text)
                .build());
    }

    public List<PostComments> getCommentsByPost(Long postId){

        return commentRepo.findByPostOrderByTimestampAsc(postRepo.findById(postId).get());
    }
}
