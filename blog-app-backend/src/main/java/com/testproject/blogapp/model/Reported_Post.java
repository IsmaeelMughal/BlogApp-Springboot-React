package com.testproject.blogapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reported_Post {
    @Id
    @GeneratedValue
    private Integer id;
    private String reason;
    @ManyToOne
    private Post post;
    @ManyToOne
    private User user;
}
