package com.testproject.blogapp.controller;

import com.testproject.blogapp.dto.ResponseDTO;
import com.testproject.blogapp.dto.SuggestionEntityDTO;
import com.testproject.blogapp.service.SuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SuggestionController {
    private final SuggestionService suggestionService;
    @PostMapping("/post/addSuggestion")
    public ResponseDTO<String> addSuggestion(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Map<String, String> suggestionData
    )
    {
        String postId = suggestionData.get("postId");
        String suggestion = suggestionData.get("suggestion");
        return suggestionService.addSuggestion(authHeader, Integer.parseInt(postId), suggestion);
    }

    @GetMapping("/post/suggestion/userSuggestions")
    public ResponseDTO<List<SuggestionEntityDTO>> userSuggestionsOnOthersPosts(
            @RequestHeader("Authorization") String authHeader
    )
    {
        return suggestionService.userSuggestionsOnOthersPosts(authHeader);
    }

    @GetMapping("/post/suggestion/otherSuggestionOnUserPost")
    public ResponseDTO<List<SuggestionEntityDTO>> otherSuggestionOnUserPost(
            @RequestHeader("Authorization") String authHeader
    )
    {
        return suggestionService.othersSuggestionsOnUserPosts(authHeader);
    }

    @PatchMapping("/post/suggestion/addReply")
    public ResponseDTO<String> addReply(@RequestHeader("Authorization") String authHeader,
                                        @RequestBody Map<String, String> suggestionData)
    {
        return suggestionService.addReply(
                authHeader,
                Integer.parseInt(suggestionData.get("suggestionId")),
                suggestionData.get("reply"));
    }

    @PatchMapping("/post/suggestion/reject/{suggestionId}")
    public ResponseDTO<String> addReply(@RequestHeader("Authorization") String authHeader,
                                        @PathVariable("suggestionId") Integer suggestionId)
    {
        return suggestionService.rejectSuggestion(authHeader, suggestionId);
    }

    @DeleteMapping("/post/suggestion/delete/{suggestionId}")
    public ResponseDTO<String> deleteSuggestion(@RequestHeader("Authorization") String authHeader,
                                        @PathVariable("suggestionId") Integer suggestionId)
    {
        return suggestionService.deleteSuggestion(authHeader, suggestionId);
    }
}
