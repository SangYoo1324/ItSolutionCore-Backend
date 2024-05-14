package com.example.ItSolutionCore.businesses.sunrise.api;


import com.example.ItSolutionCore.businesses.sunrise.BusinessVars_sunrise;
import com.example.ItSolutionCore.businesses.sunrise.service.SunrisePublicFileService;
import com.example.ItSolutionCore.common.dto.GenericResponseDto;
import com.example.ItSolutionCore.common.uni_dto.PublicFileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/public/sunrise")
@RequiredArgsConstructor
public class SunriseFileApiController {

    private final SunrisePublicFileService sunrisePublicFileService;


    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile multipartFile){

        try {
            return ResponseEntity.status(HttpStatus.OK).body(sunrisePublicFileService.upload(multipartFile, BusinessVars_sunrise.DIRECT_UPLOAD));
        } catch (IOException e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GenericResponseDto.builder().error("upload to S3 bucket failed..."));
        }

    }
}
