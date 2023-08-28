package com.testproject.blogapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    private String image;
    @ManyToOne
    private Post post;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<Comment_Like> commentLikes;
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<Reported_Comment> reportedComments;

}
