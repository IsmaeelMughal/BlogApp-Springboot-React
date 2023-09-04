package com.testproject.blogapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseDTO<T> {
    private UserEntityDTO currentUser;
    private T data;
    private HttpStatus status;
    private String message;
}
