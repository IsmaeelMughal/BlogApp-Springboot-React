package com.testproject.blogapp.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentReplyEntityDTO {
    private int id;
    private String content;
    private UserEntityDTO repliedBy;
}
