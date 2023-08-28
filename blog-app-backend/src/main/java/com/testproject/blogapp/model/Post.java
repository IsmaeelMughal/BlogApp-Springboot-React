package com.testproject.blogapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    private String image;
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Suggestion> suggestions;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Post_Like> postLikes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment_Like> commentLikes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Reported_Post> reportedPosts;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Reported_Comment> reportedComments;
}
