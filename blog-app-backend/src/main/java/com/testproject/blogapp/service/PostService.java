package com.testproject.blogapp.service;

import com.cloudinary.Cloudinary;
import com.testproject.blogapp.config.JwtService;
import com.testproject.blogapp.dto.PostEntityDTO;
import com.testproject.blogapp.dto.UserEntityDTO;
import com.testproject.blogapp.model.PostEntity;
import com.testproject.blogapp.model.Status;
import com.testproject.blogapp.model.UserEntity;
import com.testproject.blogapp.repository.PostRepository;
import com.testproject.blogapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final Cloudinary cloudinary;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public PostEntity addPost(MultipartFile file,String title, String content, String authHeader) throws IOException {
        String jwt = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(jwt);
        System.out.printf(userEmail);
        UserEntity user = userRepository.findByEmail(userEmail).orElseThrow();
        String imageUrl = imageUploadOnCloudinary(file);
        PostEntity post = new PostEntity();
        post.setUserByUserId(user);
        post.setTitle(title);
        post.setContent(content);
        post.setImage(imageUrl);
        post.setDate(new Date());
        post.setStatus(Status.UNAPPROVED);
        postRepository.save(post);
        return post;
    }

    private String imageUploadOnCloudinary(MultipartFile file) throws IOException {
        Map<String, String> uploadResult = cloudinary.uploader().upload(file.getBytes(), Map.of());
        System.out.println(uploadResult);
        return uploadResult.get("url");
    }
    public List<PostEntityDTO> getUserPosts(String authHeader)
    {
        String jwt = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(jwt);
        System.out.printf(userEmail);
        UserEntity user = userRepository.findByEmail(userEmail).orElseThrow();
        List<PostEntity> postEntityList = postRepository.findByUserByUserId_Id(user.getId());

        List<PostEntityDTO> postEntityDTOS = postEntityList.stream().map(postEntity ->
                new PostEntityDTO(postEntity.getId(),
                        postEntity.getTitle(),
                        postEntity.getContent(),
                        postEntity.getImage(),
                        postEntity.getStatus().name(),
                        postEntity.getDate(),
                        new UserEntityDTO(postEntity.getUserByUserId().getId(),
                                postEntity.getUserByUserId().getName(),
                                postEntity.getUserByUserId().getEmail(),
                                postEntity.getUserByUserId().getRole().name()))
        ).toList();

        return postEntityDTOS;
    }

    public PostEntityDTO getPost(Integer postId) {
        Optional<PostEntity> optionalPost = postRepository.findById(postId);
        if( optionalPost.isPresent()) {
            PostEntity post = optionalPost.get();
            PostEntityDTO postEntityDTO = new PostEntityDTO(post.getId(),
                    post.getTitle(), post.getContent(), post.getImage(),
                    post.getStatus().name(), post.getDate(),
                    new UserEntityDTO(
                            post.getUserByUserId().getId(),
                            post.getUserByUserId().getName(),
                            post.getUserByUserId().getEmail(),
                            post.getUserByUserId().getRole().name()
                    ));
            return postEntityDTO;
        }
        else {
            throw new IllegalArgumentException("Post Not Found");
        }
    }


    public List<PostEntityDTO> getAllUnapprovedPosts()
    {
        List<PostEntity> postEntityList = postRepository.findByStatus(Status.UNAPPROVED);
        List<PostEntityDTO> postEntityDTOS = postEntityList.stream().map(postEntity ->
                new PostEntityDTO(postEntity.getId(),
                        postEntity.getTitle(),
                        postEntity.getContent(),
                        postEntity.getImage(),
                        postEntity.getStatus().name(),
                        postEntity.getDate(),
                        new UserEntityDTO(postEntity.getUserByUserId().getId(),
                                postEntity.getUserByUserId().getName(),
                                postEntity.getUserByUserId().getEmail(),
                                postEntity.getUserByUserId().getRole().name()))
        ).toList();
        return postEntityDTOS;
    }

    public boolean approvePostById(Integer id)
    {
       Optional<PostEntity> post = postRepository.findById(id);
       if(post.isPresent())
       {
           PostEntity postEntity = post.get();
           postEntity.setStatus(Status.APPROVED);
           postRepository.save(postEntity);
           return true;
       }
       else
       {
           return false;
       }
    }

    public boolean deletePostById(Integer id)
    {
        Optional<PostEntity> post = postRepository.findById(id);
        if(post.isPresent())
        {
            postRepository.deleteById(id);
            return true;
        }
        else
        {
            return false;
        }
    }

    public List<PostEntityDTO> getAllApprovedPosts() {
        List<PostEntity> postEntityList = postRepository.findByStatus(Status.APPROVED);
        List<PostEntityDTO> postEntityDTOS = postEntityList.stream().map(postEntity ->
                new PostEntityDTO(postEntity.getId(),
                        postEntity.getTitle(),
                        postEntity.getContent(),
                        postEntity.getImage(),
                        postEntity.getStatus().name(),
                        postEntity.getDate(),
                        new UserEntityDTO(postEntity.getUserByUserId().getId(),
                                postEntity.getUserByUserId().getName(),
                                postEntity.getUserByUserId().getEmail(),
                                postEntity.getUserByUserId().getRole().name()))
        ).toList();
        return postEntityDTOS;
    }

    public boolean postExistsById(Integer postId)
    {
        Optional<PostEntity> optionalPost =postRepository.findById(postId);
        return optionalPost.isPresent();
    }
}
