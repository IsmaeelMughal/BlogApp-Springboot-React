package com.testproject.blogapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppDetailsDTO {
    private Integer totalPosts;
    private Integer totalComments;
    private Integer totalUsers;
    private Integer TotalLikes;
}
