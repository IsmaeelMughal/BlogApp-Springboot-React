package com.testproject.blogapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String email;
    private String password;
    @Enumerated
    private Role Role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Suggestion> suggestions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post_Like> postLikes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment_Like> commentLikes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reported_Post> reportedPost;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reported_Comment> reportedComments;
}
