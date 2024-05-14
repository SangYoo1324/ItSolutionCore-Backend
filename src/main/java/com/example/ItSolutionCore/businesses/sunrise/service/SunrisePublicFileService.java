package com.example.ItSolutionCore.businesses.sunrise.service;


import com.example.ItSolutionCore.businesses.sunrise.BusinessVars_sunrise;
import com.example.ItSolutionCore.businesses.sunrise.entity.EventPost;
import com.example.ItSolutionCore.businesses.sunrise.entity.PhotoEvent;
import com.example.ItSolutionCore.businesses.sunrise.entity.SunriseFile;
import com.example.ItSolutionCore.businesses.sunrise.repo.SunriseFileRepository;
import com.example.ItSolutionCore.common.service.S3Service;
import com.example.ItSolutionCore.common.uni_dto.PublicFileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class SunrisePublicFileService {

    private final SunriseFileRepository sunriseFileRepository;

    private final S3Service s3Service;

    public SunriseFile upload(MultipartFile multipartFile, String category) throws IOException {

       PublicFileDto publicFileDto =  s3Service.imageUpload(multipartFile,BusinessVars_sunrise.BUSINESS, category);

      return sunriseFileRepository.save(SunriseFile.builder()
                       .contentType(publicFileDto.getContentType())
                       .size(publicFileDto.getSize())
                       .s3_url(publicFileDto.getS3_url())
                       .filePath(publicFileDto.getFilePath()) // to determine it's uploaded from prod OR dev env
                       .registeredDate(publicFileDto.getRegisteredDate())
                       .fileName(publicFileDto.getFileName())
                       .s3_url(publicFileDto.getS3_url())
               .build()
       );

    }
    // overloaded for upload with photoEvent
    public SunriseFile upload(MultipartFile multipartFile, String category, Object entity) throws IOException {

        PublicFileDto publicFileDto =  s3Service.imageUpload(multipartFile, BusinessVars_sunrise.BUSINESS, category);

        SunriseFile file = SunriseFile.builder()
                .contentType(publicFileDto.getContentType())
                .size(publicFileDto.getSize())
                .s3_url(publicFileDto.getS3_url())
                .filePath(publicFileDto.getFilePath()) // to determine it's uploaded from prod OR dev env
                .registeredDate(publicFileDto.getRegisteredDate())
                .fileName(publicFileDto.getFileName())
                .s3_url(publicFileDto.getS3_url())
                .build();
//         .photoEvent(photoEvent)
        switch (entity.getClass().getSimpleName()){
            case "PhotoEvent":
                file.setPhotoEvent((PhotoEvent) entity);
                break;


            default:
                break;

        }



// because initial publicFileDto has no ID cuz it's before SQL transaction
      SunriseFile savedFile = sunriseFileRepository.save(file);
        publicFileDto.setId(savedFile.getId());

        return savedFile;
    }

}
