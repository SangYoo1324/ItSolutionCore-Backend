package com.example.ItSolutionCore.businesses.sunrise.api_service.api;


import com.example.ItSolutionCore.businesses.sunrise.api_service.service.EventPostService;
import com.example.ItSolutionCore.businesses.sunrise.data.dto.EventPostDto;
import com.example.ItSolutionCore.common.dto.GenericResponseDto;
import com.example.ItSolutionCore.common.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/public/sunrise")
public class EventPostApiController {


    private final EventPostService eventPostService;

    @PostMapping(value = "/event")
    public ResponseEntity<?> uploadRegular(@RequestParam("title") String title,
                                           @RequestParam("timeStamp") Long timeStamp,
                                           @RequestParam("time") String time,
                                           @RequestParam("description") String description,
                                           @RequestParam("file") MultipartFile multipartFile) {

        try {
            eventPostService.postRegularEvent(title, timeStamp, time, description, multipartFile);
            return ResponseEntity.status(HttpStatus.OK).body(GenericResponseDto.builder().response("Successfully uploaded")
                    .build());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Error occured during uploading multipartFile to S3");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GenericResponseDto.builder().errorCode(500)
                    .build());
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GenericResponseDto.builder().errorCode(500)
                    .build());
        }


    }


    @GetMapping(value = "/events")
    public ResponseEntity<?> fetchAll() {


        return ResponseEntity.status(HttpStatus.OK).body(eventPostService.fetchAll());
    }


    @PostMapping(value = "/event/recurring/weekly")
    public ResponseEntity<?> uploadWeekly(@RequestParam("title") String title,
                                          @RequestParam("date") Long date,
                                          @RequestParam("time") String time,
                                          @RequestParam("description") String description,
                                          @RequestParam("file") MultipartFile multipartFile) {


        try {
            eventPostService.postWeeklyEvent(title, date, time, description, multipartFile);
            return ResponseEntity.status(HttpStatus.OK).body(GenericResponseDto.builder().response("Upload weekly events complete").build());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GenericResponseDto.builder().errorCode(500).build());
        }


    }

    //    @PostMapping(value="/event/recurring/monthly")
//    public ResponseEntity<EventPostDto> upload(@RequestParam("title") String title,
//                                               @RequestParam("date") String date,
//                                               @RequestParam("time") String time,
//                                               @RequestParam("description") String description,
//                                               @RequestParam("file") MultipartFile multipartFile){
//
//
//
//
//
//    }
    @DeleteMapping(value = "/event/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        try {
            eventPostService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(GenericResponseDto.builder().response("Deleted").build());
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).body(GenericResponseDto.builder().errorCode(500).build());
        }

    }
}