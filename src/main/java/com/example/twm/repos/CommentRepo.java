package com.example.twm.repos;

import com.example.twm.domain.post.Post;
import com.example.twm.domain.post.PostComments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<PostComments, Long> {
List<PostComments> findByPostOrderByTimestampAsc(Post post);
}
