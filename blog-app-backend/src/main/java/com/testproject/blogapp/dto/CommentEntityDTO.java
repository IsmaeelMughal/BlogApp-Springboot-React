package com.testproject.blogapp.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentEntityDTO {
    private Integer id;
    private String content;
    private UserEntityDTO commentedBy;
    private List<CommentReplyEntityDTO> replies;
}
