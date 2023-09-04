package com.testproject.blogapp.service;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageUploadingService {
    private final Cloudinary cloudinary;

    public String imageUploadOnCloudinary(MultipartFile file) throws IOException {
        Map<String, String> uploadResult = cloudinary.uploader().upload(file.getBytes(), Map.of());
        System.out.println(uploadResult);
        return uploadResult.get("url");
    }
}
