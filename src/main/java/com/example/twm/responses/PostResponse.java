package com.example.twm.responses;

import com.example.twm.domain.post.PostLikes;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostResponse {
    private Long id;
    private Long timestamp;
    private String text;
    private String author;
    private Long authorId;
    List<PostLikes> postLikes;
    List<PostCommentsResponse> postComments;
}
