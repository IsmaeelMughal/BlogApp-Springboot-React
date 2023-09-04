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
public class AdminPostEntityDTO {
    private Integer id;
    private String title;
    private String status;
    private Date date;
    private UserEntityDTO postedBy;
    private Integer numberOfReports;
    private Integer numberOfLikes;
}
