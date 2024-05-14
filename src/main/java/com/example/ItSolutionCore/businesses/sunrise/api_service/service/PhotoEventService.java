package com.example.ItSolutionCore.businesses.sunrise.api_service.service;


import com.example.ItSolutionCore.businesses.sunrise.BusinessVars_sunrise;
import com.example.ItSolutionCore.businesses.sunrise.data.dto.PhotoEventDto;
import com.example.ItSolutionCore.businesses.sunrise.data.entity.PhotoEvent;
import com.example.ItSolutionCore.businesses.sunrise.repo.PhotoEventRepository;
import com.example.ItSolutionCore.common.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PhotoEventService {


    private final SunrisePublicFileService sunrisePublicFileService;
    private final PhotoEventRepository photoEventRepository;

    public void uploadPhotoEvent(String title, String subTitle, MultipartFile[] multipartFiles) throws IOException {

        // Make eventPhoto entity- parent
        PhotoEvent photoEvent = photoEventRepository.save(PhotoEvent.builder()
                        .title(title)
                        .subTitle(subTitle)
                        .date(Timestamp.valueOf(LocalDateTime.now()))
                .build());


        // Make photo entities - child
        for(MultipartFile file :multipartFiles){
            // this method will upload the file into s3 bucket && persist to DB, put photoEvent as an FK
          sunrisePublicFileService.upload(file, BusinessVars_sunrise.PHOTOEVENT, photoEvent);
        }
    }

    public PhotoEventDto fetchPhotoEvent(Long id) throws DataNotFoundException {


       PhotoEvent photoEvent =  photoEventRepository.fetchPhotoEvent(id).orElseThrow(()-> new DataNotFoundException("cannot find photoEvent with given id"));

      return photoEvent.toDto();

    }

    public List<PhotoEventDto> fetchPhotoEvents() {
        // it doesn't fetch all the related photoes for each photoEvent.
        // If later required, use fetch join
        return photoEventRepository.findAll_with_img().stream().map(PhotoEvent::toDto).collect(Collectors.toList());
    }
    public void delete(Long id) throws DataNotFoundException {



        photoEventRepository.delete(
                photoEventRepository.findById(id).orElseThrow(()-> new DataNotFoundException("photoEvent not found"))
        );
    }

}
