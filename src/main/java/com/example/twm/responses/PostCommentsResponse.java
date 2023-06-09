package com.example.twm.responses;

import com.example.twm.domain.User;
import com.example.twm.domain.post.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PostCommentsResponse {
    private Long id;
    private Long timestamp;
    private String text;
    private Long postId;
    private Long userId;
    private String username;
}
