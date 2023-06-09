package com.example.twm.domain.post;

import com.example.twm.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date timestamp;
    private String text;
    private String photo;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "post")
    List<PostLikes> postLikes;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "post")
    List<PostComments> postComments;

}
