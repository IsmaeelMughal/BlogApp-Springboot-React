package com.testproject.blogapp.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportedCommentDTO {
    private Integer commentId;
    private Integer postId;
    private String comment;
    private Integer numberOfLikes;
    private Integer numberOfReports;
    private UserEntityDTO commentedBy;
}
