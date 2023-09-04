package com.testproject.blogapp.service;


import com.testproject.blogapp.config.JwtService;
import com.testproject.blogapp.dto.PostEntityDTO;
import com.testproject.blogapp.dto.ResponseDTO;
import com.testproject.blogapp.dto.SuggestionEntityDTO;
import com.testproject.blogapp.dto.UserEntityDTO;
import com.testproject.blogapp.model.PostEntity;
import com.testproject.blogapp.model.SuggestionEntity;
import com.testproject.blogapp.model.SuggestionStatus;
import com.testproject.blogapp.model.UserEntity;
import com.testproject.blogapp.repository.PostRepository;
import com.testproject.blogapp.repository.SuggestionRepository;
import com.testproject.blogapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SuggestionService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UserService userService;
    private final SuggestionRepository suggestionRepository;
    public ResponseDTO<String> addSuggestion(String authHeader, Integer postId, String suggestion)
    {
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);
        UserEntity user = userRepository.findByEmail(userEntityDTO.getEmail()).orElseThrow();
        Optional<PostEntity> optionalPost =postRepository.findById(postId);
        if(optionalPost.isEmpty())
        {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.NOT_FOUND, "Post Does Not Exists");
        }

        SuggestionEntity suggestionEntity = new SuggestionEntity();
        suggestionEntity.setSuggestion(suggestion);
        suggestionEntity.setStatus(SuggestionStatus.PENDING);
        suggestionEntity.setReply("");
        suggestionEntity.setUserByUserId(user);
        suggestionEntity.setPostByPostId(optionalPost.get());

        suggestionRepository.save(suggestionEntity);
        return new ResponseDTO<>(userEntityDTO, "Thank You for your Suggestion!!!", HttpStatus.OK, "Thank You for your Suggestion!!!");
    }

    public ResponseDTO<List<SuggestionEntityDTO>> userSuggestionsOnOthersPosts(String authHeader)
    {
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);
        UserEntity user = userRepository.findByEmail(userEntityDTO.getEmail()).orElseThrow();

        List<SuggestionEntity> suggestionEntityList = suggestionRepository.findByUserByUserId(user);

        List<SuggestionEntityDTO> suggestionEntityDTOList = suggestionEntityList.stream().map(suggestionEntity -> {
            return new SuggestionEntityDTO(
                    suggestionEntity.getId(),
                    suggestionEntity.getSuggestion(),
                    suggestionEntity.getReply(),
                    suggestionEntity.getStatus(),
                    new UserEntityDTO(
                            suggestionEntity.getUserByUserId().getId(),
                            suggestionEntity.getUserByUserId().getName(),
                            suggestionEntity.getUserByUserId().getEmail(),
                            suggestionEntity.getUserByUserId().getRole().name()),
                    suggestionEntity.getPostByPostId().getId(),
                    suggestionEntity.getPostByPostId().getTitle()
            );
        }).toList();

        return new ResponseDTO<>(userEntityDTO, suggestionEntityDTOList, HttpStatus.OK, "List of Your Suggestions on others Posts!!!!");
    }

    public ResponseDTO<List<SuggestionEntityDTO>> othersSuggestionsOnUserPosts(String authHeader)
    {
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);
        UserEntity user = userRepository.findByEmail(userEntityDTO.getEmail()).orElseThrow();
        List<SuggestionEntity>  suggestionEntityList= suggestionRepository.findByPostByPostId_UserByUserId_Id(user.getId());
        List<SuggestionEntityDTO> suggestionEntityDTOList = suggestionEntityList.stream().map(suggestionEntity -> {
            return new SuggestionEntityDTO(
                    suggestionEntity.getId(),
                    suggestionEntity.getSuggestion(),
                    suggestionEntity.getReply(),
                    suggestionEntity.getStatus(),
                    new UserEntityDTO(
                            suggestionEntity.getUserByUserId().getId(),
                            suggestionEntity.getUserByUserId().getName(),
                            suggestionEntity.getUserByUserId().getEmail(),
                            suggestionEntity.getUserByUserId().getRole().name()),
                    suggestionEntity.getPostByPostId().getId(),
                    suggestionEntity.getPostByPostId().getTitle()
            );
        }).toList();
        return new ResponseDTO<>(userEntityDTO, suggestionEntityDTOList, HttpStatus.OK, "List of Others Suggestions on Your Posts!!!!");
    }

    public ResponseDTO<String> addReply(String authHeader, Integer suggestionId, String reply)
    {
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);
        Optional<SuggestionEntity> optionalSuggestionEntity = suggestionRepository.findById(suggestionId);
        if(optionalSuggestionEntity.isEmpty())
        {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.NOT_FOUND, "Suggestion Does Not Exists!!!");
        }

        SuggestionEntity suggestionEntity = optionalSuggestionEntity.get();
        suggestionEntity.setReply(reply);
        suggestionEntity.setStatus(SuggestionStatus.REPLIED);
        suggestionRepository.save(suggestionEntity);
        return new ResponseDTO<>(userEntityDTO, "Suggestion Replied Successfully!!!", HttpStatus.OK, "Suggestion Replied Successfully!!!");
    }

    public ResponseDTO<String> rejectSuggestion(String authHeader, Integer suggestionId)
    {
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);
        Optional<SuggestionEntity> optionalSuggestionEntity = suggestionRepository.findById(suggestionId);
        if(optionalSuggestionEntity.isEmpty())
        {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.NOT_FOUND, "Suggestion Does Not Exists!!!");
        }

        SuggestionEntity suggestionEntity = optionalSuggestionEntity.get();
        suggestionEntity.setStatus(SuggestionStatus.REJECTED);
        suggestionRepository.save(suggestionEntity);
        return new ResponseDTO<>(userEntityDTO, "Suggestion Rejected!!!", HttpStatus.OK, "Suggestion Rejected!!!");
    }

    public ResponseDTO<String> deleteSuggestion(String authHeader, Integer suggestionId)
    {
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);
        Optional<SuggestionEntity> optionalSuggestionEntity = suggestionRepository.findById(suggestionId);
        if(optionalSuggestionEntity.isEmpty())
        {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.NOT_FOUND, "Suggestion Does Not Exists!!!");
        }

        suggestionRepository.deleteById(suggestionId);
        return new ResponseDTO<>(userEntityDTO, "Suggestion Deleted Successfully!!!", HttpStatus.OK, "Suggestion Deleted Successfully!!!");
    }

}
