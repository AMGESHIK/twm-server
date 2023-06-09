package com.example.twm.controllers;

import com.example.twm.domain.post.Post;
import com.example.twm.domain.post.PostComments;
import com.example.twm.responses.PostCommentsResponse;
import com.example.twm.responses.PostResponse;
import com.example.twm.service.impl.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("post")
public class PostsController {
    private final PostService postService;

    @PostMapping()
    public ResponseEntity<String> createPost(@RequestParam("text") String text, @RequestParam("file") MultipartFile file) {
        postService.createPost(text, file);
        return ResponseEntity.ok("ok");
    }

    @GetMapping()
    public ResponseEntity<?> getPosts(@RequestParam Long count) {
        List<Post> post = postService.getPosts(count);
        List<PostResponse> postResponse = new ArrayList<>();
        post.forEach(x -> {
            List<PostComments> postComments = postService.getCommentsByPost(x.getId());
            List<PostCommentsResponse> postCommentsResponse = new ArrayList<>();
            postComments.forEach(y -> {
                postCommentsResponse.add(PostCommentsResponse
                        .builder()
                                .id(y.getId())
                                .postId(y.getPost().getId())
                                .userId(y.getUser().getId())
                                .username(y.getUser().getUsername())
                                .text(y.getText())
                                .timestamp(y.getTimestamp().getTime())
                        .build());
            });
            postResponse.add(PostResponse
                    .builder()
                    .id(x.getId())
                    .author(x.getAuthor().getUsername())
                    .authorId(x.getAuthor().getId())
                    .text(x.getText())
                    .timestamp(x.getTimestamp().getTime())
                    .postLikes(x.getPostLikes())
                    .postComments(postCommentsResponse)
                    .build());
        });
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/photo")
    public ResponseEntity<?> getPostPhoto(@RequestParam Long id) throws IOException {
        Resource file = postService.getPostPhoto(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .contentType(MediaType.parseMediaType(Files.probeContentType(file.getFile().toPath())))
                .body(file);
    }

    @PostMapping("/comment")
    public ResponseEntity<?> setComment(@RequestBody HashMap<String, String> comBody) {
        postService.setComment(comBody.get("text"), Long.parseLong(comBody.get("postId")));
        return ResponseEntity.ok("ok");
    }
}
