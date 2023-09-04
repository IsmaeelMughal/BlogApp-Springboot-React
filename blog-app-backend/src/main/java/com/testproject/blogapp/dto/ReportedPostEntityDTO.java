package com.testproject.blogapp.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReportedPostEntityDTO {
    private Integer postId;
    private String postedBy;
    private String title;
    private Date date;
    private int numberOfReports;
    private int numberOfLikes;
}
