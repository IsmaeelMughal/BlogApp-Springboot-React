package com.testproject.blogapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostEntityDTO {
    private Integer id;
    private String title;
    private String content;
    private String image;
    private String status;
    private Date date;
    private UserEntityDTO postedBy;
}
