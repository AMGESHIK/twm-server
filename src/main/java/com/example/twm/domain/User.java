package com.example.twm.domain;

import com.example.twm.domain.chat.ChatMessage;
import com.example.twm.domain.chat.ChatRoom;
import com.example.twm.domain.post.Post;
import com.example.twm.domain.post.PostComments;
import com.example.twm.domain.post.PostLikes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usr")
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Email
    @NotBlank
    @Size(max = 50)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Size(min = 3, max = 20)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Size(max=40)
    @Column(name = "name")
    private String name;

    @Size(max=600)
    @Column(name = "about")
    private String about;

    @NotBlank
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "photo")
    private String photo;
    @Column(name = "address")
    private String address;
    @Column(name = "mobile")
    private String mobile;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Role> roles;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private List<Program> programs;

    @OneToMany(mappedBy = "sender", fetch = FetchType.EAGER)
    private List<ChatMessage> chatMessages;

    @ManyToMany
    @JoinTable(name = "user_chatroom",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chatroom_id"))
    @JsonIgnore
    private List<ChatRoom> chatRooms;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "author")
    private List<Post> posts;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private List<PostLikes> postLikes;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private List<PostComments> postComments;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
