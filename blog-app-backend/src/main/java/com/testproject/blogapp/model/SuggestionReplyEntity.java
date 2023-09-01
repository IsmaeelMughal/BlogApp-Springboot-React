package com.testproject.blogapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "suggestion_reply", schema = "blog_app")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SuggestionReplyEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "content")
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userByUserId;

    @ManyToOne
    @JoinColumn(name = "suggestion_id", referencedColumnName = "id")
    private SuggestionEntity suggestionBySuggestionId;
}
