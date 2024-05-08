package com.example.ItSolutionCore.common.api;

import com.example.ItSolutionCore.common.auth.service.S3Service;
import com.example.ItSolutionCore.common.dto.GenericResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/*
* Multiple purpose for file upload on S3 bucket.
* */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class FileApiController {

    private final S3Service s3Service;

    @PostMapping("/upload")
    private ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile multipartFile){

        try {

            return ResponseEntity.status(HttpStatus.OK).body(  GenericResponseDto.builder().response(s3Service.imageUpload(multipartFile)).build());
        } catch (IOException e) {
           e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(  GenericResponseDto.builder().error("error occured on storing image").errorCode(500).build());

        }

    }

}
