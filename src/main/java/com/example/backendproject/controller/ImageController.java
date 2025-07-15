package com.example.backendproject.controller;

import com.example.backendproject.entity.Image;
import com.example.backendproject.repository.ImageRepository;
import com.example.backendproject.service.S3Service;
import jakarta.servlet.FilterChain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final S3Service s3Service;
    private final ImageRepository imageRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("images", imageRepository.findAll());
        return "index";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file")MultipartFile file) throws IOException {
        String url = s3Service.uploadFile(file);
        Image image = new Image();
        image.setOriginalName(file.getOriginalFilename());
        image.setS3Url(url);
        imageRepository.save(image);
        return "redirect:/";
    }
}
