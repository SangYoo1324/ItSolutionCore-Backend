package com.example.ItSolutionCore.businesses.sunrise.api;


import com.example.ItSolutionCore.businesses.sunrise.dto.PhotoEventDto;
import com.example.ItSolutionCore.businesses.sunrise.service.PhotoEventService;
import com.example.ItSolutionCore.common.dto.GenericResponseDto;
import com.example.ItSolutionCore.common.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/public/sunrise")
public class PhotoEventApiController {

    private final PhotoEventService photoEventService;

    @PostMapping("/photo")
    public ResponseEntity<?> upload(@RequestParam("title")String title,
                                                @RequestParam("subTitle") String subTitle,
                                                @RequestParam("file") MultipartFile[] multipartFiles) {


        try {
            photoEventService.uploadPhotoEvent(title,subTitle, multipartFiles);
            return ResponseEntity.status(HttpStatus.OK).body(GenericResponseDto.builder().response("Successfully uploaded photoEvent with Photos").build());

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).body(GenericResponseDto.builder().error("error from uploading file to s3 bucket").build());

        }


    }

    @GetMapping("/photo/{id}")
    public ResponseEntity<?> fetch(@PathVariable Long id){

        try {
            return ResponseEntity.status(HttpStatus.OK).body(photoEventService.fetchPhotoEvent(id));
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).body(GenericResponseDto.builder().error("cannot find the photoEvent with given ID"));
        }

    }

    @GetMapping("/photos")
    public ResponseEntity<?> fetchAll(){
        return ResponseEntity.status(HttpStatus.OK).body(photoEventService.fetchPhotoEvents());
    }

}






