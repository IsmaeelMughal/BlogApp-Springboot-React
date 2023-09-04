package com.testproject.blogapp.dto;

import com.testproject.blogapp.model.SuggestionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SuggestionEntityDTO {
    private Integer id;
    private String suggestion;
    private String reply;
    private SuggestionStatus status;
    private UserEntityDTO suggestedBy;
    private Integer onPostId;
    private String onPostTitle;
}
