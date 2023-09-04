package com.testproject.blogapp.service;

import com.testproject.blogapp.config.JwtService;
import com.testproject.blogapp.dto.ResponseDTO;
import com.testproject.blogapp.dto.UserEntityDTO;
import com.testproject.blogapp.model.PostEntity;
import com.testproject.blogapp.model.PostLikeEntity;
import com.testproject.blogapp.model.UserEntity;
import com.testproject.blogapp.repository.PostLikeRepository;
import com.testproject.blogapp.repository.PostRepository;
import com.testproject.blogapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;
    private final JwtService jwtService;
    private final UserService userService;
    public ResponseDTO<String> postLikeByIds(String authHeader, Integer postId)
    {
        String jwt = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(jwt);
        Optional<UserEntity> optionalUser = userRepository.findByEmail(userEmail);
        Optional<PostEntity> optionalPost =postRepository.findById(postId);
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);
        if(optionalPost.isEmpty())
        {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.NOT_FOUND, "Post Does Not Exists");
        }
        if (optionalUser.isEmpty())
        {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.NOT_FOUND, "User Does Not Exists");
        }

        Optional<PostLikeEntity> optionalPostLikeEntity =
                postLikeRepository.findByPostByPostIdAndUserByUserId(optionalPost.get(), optionalUser.get());

        if (optionalPostLikeEntity.isPresent())
        {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.BAD_REQUEST, "You have already liked this Post!!!");
        }
        PostLikeEntity postLikeEntity = new PostLikeEntity();
        postLikeEntity.setUserByUserId(optionalUser.get());
        postLikeEntity.setPostByPostId(optionalPost.get());
        postLikeRepository.save(postLikeEntity);
        return new ResponseDTO<>(userEntityDTO, "Post Liked Successfully!!!", HttpStatus.OK, "Post Liked Successfully!!!");
    }
}
