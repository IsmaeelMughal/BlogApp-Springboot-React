package com.testproject.blogapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntityDTO {
    private Integer id;
    private String name;
    private String email;
    private String role;
}
