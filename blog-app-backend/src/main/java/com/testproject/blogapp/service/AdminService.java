package com.testproject.blogapp.service;

import com.testproject.blogapp.config.JwtService;
import com.testproject.blogapp.dto.*;
import com.testproject.blogapp.model.PostEntity;
import com.testproject.blogapp.model.Role;
import com.testproject.blogapp.model.SuggestionEntity;
import com.testproject.blogapp.model.UserEntity;
import com.testproject.blogapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final ReportedPostRepository reportedPostRepository;
    private final SuggestionRepository suggestionRepository;
    private final CommentRepository commentRepository;
    public ResponseDTO<List<UserEntityDTO>> getAllUsersByRole(String authHeader, Role role)
    {
        String jwt = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(jwt);
        UserEntity user = userRepository.findByEmail(userEmail).orElseThrow();
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);
        if (user.getRole() != Role.ROLE_ADMIN)
        {
            return new ResponseDTO<>(userEntityDTO,null, HttpStatus.UNAUTHORIZED, "You are Not Authorized!!!!");
        }
        List<UserEntity> userEntityList = userRepository.findByRole(role);
        List<UserEntityDTO> userEntityDTOList = userEntityList.stream().map(
                userEntity -> {
                    return new UserEntityDTO(
                            userEntity.getId(),
                            userEntity.getName(),
                            userEntity.getEmail(),
                            userEntity.getRole().name()
                    );
                }
        ).toList();
        return new ResponseDTO<>(userEntityDTO, userEntityDTOList, HttpStatus.OK, "List of All Users!!!");
    }

    public ResponseDTO<String> deleteUserById(String authHeader, Integer id)
    {
        String jwt = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(jwt);
        UserEntity user = userRepository.findByEmail(userEmail).orElseThrow();
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);
        if (user.getRole() != Role.ROLE_ADMIN)
        {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.UNAUTHORIZED, "You are Not Authorized!!!!");
        }
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty())
        {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.NOT_FOUND, "User Does Not Exist!!!");
        }
        userRepository.deleteById(id);
        return new ResponseDTO<>(userEntityDTO, "User Deleted Successfully!!!", HttpStatus.OK,"User Deleted Successfully!!!");
    }

    public ResponseDTO<List<AdminPostEntityDTO>> getAllPosts(String authHeader) {
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);
        List<PostEntity> postEntityList = postRepository.findAll();

        List<AdminPostEntityDTO> adminPostEntityDTOList = postEntityList.stream().map(postEntity -> {
            return new AdminPostEntityDTO(
                    postEntity.getId(),
                    postEntity.getTitle(),
                    postEntity.getStatus().name(),
                    postEntity.getDate(),
                    new UserEntityDTO(
                            postEntity.getUserByUserId().getId(),
                            postEntity.getUserByUserId().getName(),
                            postEntity.getUserByUserId().getEmail(),
                            postEntity.getUserByUserId().getRole().name()
                    ),
                    reportedPostRepository.countReportsForPost(postEntity.getId()),
                    postLikeRepository.countLikesForPost(postEntity.getId())
            );
        }).toList();

        return new ResponseDTO<>(userEntityDTO, adminPostEntityDTOList, HttpStatus.OK, "List of All Posts in Blog App!!!");
    }

    public ResponseDTO<List<SuggestionEntityDTO>> getAllSuggestions(String authHeader) {
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);
        List<SuggestionEntity>  suggestionEntityList= suggestionRepository.findAll();
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
        return new ResponseDTO<>(userEntityDTO, suggestionEntityDTOList, HttpStatus.OK, "List of All Suggestions!!!!");
    }

    public ResponseDTO<AppDetailsDTO> getCountDetails(String authHeader) {
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);
        AppDetailsDTO appDetailsDTO = new AppDetailsDTO(
                postRepository.countOfAllPosts(),
                commentRepository.countTotalComments(),
                userRepository.countOfAllUsers(),
                postLikeRepository.countAllLikesForPost()
        );

        return new ResponseDTO<>(userEntityDTO, appDetailsDTO, HttpStatus.OK, "App Details!!!");

    }
}
