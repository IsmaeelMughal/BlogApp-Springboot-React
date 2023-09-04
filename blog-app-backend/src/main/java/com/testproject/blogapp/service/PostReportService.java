package com.testproject.blogapp.service;

import com.testproject.blogapp.config.JwtService;
import com.testproject.blogapp.dto.ReportedPostEntityDTO;
import com.testproject.blogapp.dto.ResponseDTO;
import com.testproject.blogapp.dto.UserEntityDTO;
import com.testproject.blogapp.model.PostEntity;
import com.testproject.blogapp.model.ReportedPostEntity;
import com.testproject.blogapp.model.UserEntity;
import com.testproject.blogapp.repository.PostLikeRepository;
import com.testproject.blogapp.repository.PostRepository;
import com.testproject.blogapp.repository.ReportedPostRepository;
import com.testproject.blogapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostReportService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private  final PostRepository postRepository;
    private final ReportedPostRepository reportedPostRepository;
    private final UserService userService;
    private final PostLikeRepository postLikeRepository;

    public ResponseDTO<String> postReportById(String authHeader, Integer postId)
    {
        String jwt = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(jwt);
        Optional<UserEntity> optionalUser = userRepository.findByEmail(userEmail);
        Optional<PostEntity> optionalPost =postRepository.findById(postId);

        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);

        if(optionalPost.isEmpty())
        {
            return new ResponseDTO<>(userEntityDTO,null, HttpStatus.NOT_FOUND, "Post Does Not Exists");
        }
        if (optionalUser.isEmpty())
        {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.NOT_FOUND, "User Does Not Exists");
        }
        Optional<ReportedPostEntity> optionalPostLikeEntity =
                reportedPostRepository.findByPostByPostIdAndUserByUserId(optionalPost.get(), optionalUser.get());
        if (optionalPostLikeEntity.isPresent())
        {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.BAD_REQUEST, "You have already Reported this Post!!!");
        }
        ReportedPostEntity reportedPost = new ReportedPostEntity();
        reportedPost.setUserByUserId(optionalUser.get());
        reportedPost.setPostByPostId(optionalPost.get());
        reportedPostRepository.save(reportedPost);
        return new ResponseDTO<>(userEntityDTO, "Post Reported Successfully!!!", HttpStatus.OK, "Post Reported Successfully!!!");
    }


    public ResponseDTO<List<ReportedPostEntityDTO>> getAllReportedPosts(String authHeader)
    {
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);
        List<PostEntity> reportedPostEntities = reportedPostRepository.findAllUniqueReportedPosts();
        List<ReportedPostEntityDTO> reportedPostEntityDTOS =
                reportedPostEntities.stream().map(
                postEntity -> {
                    return new ReportedPostEntityDTO(
                            postEntity.getId(),
                            postEntity.getUserByUserId().getName(),
                            postEntity.getTitle(),
                            postEntity.getDate(),
                            reportedPostRepository.countReportsForPost(postEntity.getId()),
                            postLikeRepository.countLikesForPost(postEntity.getId())
                    );
                }
        ).toList();
        return new ResponseDTO<>(userEntityDTO, reportedPostEntityDTOS, HttpStatus.OK,"List of Reported Posts!!!");
    }
}
