package com.example.twm.repos;

import com.example.twm.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p ORDER BY p.timestamp ASC")
    List<Post> findPostsByRowNumRange(@Param("startRow") Long startRow, @Param("endRow") Long endRow);

}
