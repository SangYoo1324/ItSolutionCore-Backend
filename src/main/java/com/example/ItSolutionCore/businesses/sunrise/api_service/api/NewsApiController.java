package com.example.ItSolutionCore.businesses.sunrise.api_service.api;

import com.example.ItSolutionCore.businesses.sunrise.api_service.service.NewsService;
import com.example.ItSolutionCore.common.dto.GenericResponseDto;
import com.example.ItSolutionCore.common.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/public/sunrise")
public class NewsApiController {

    private final NewsService newsService;

    @PostMapping(value="/news")
    public ResponseEntity<?> postNews(@RequestParam("file") MultipartFile file,
                                      @RequestParam("title") String title,
                                      @RequestParam("startDate") String startDate,
                                      @RequestParam("endDate") String endDate,
                                      @RequestParam("time")String time,
                                      @RequestParam("dayOfWeek") String dayOfWeek,
                                      @RequestParam("recurring") boolean recurring,
                                      @RequestParam("description")String description
    ){

        try {
            newsService.postNews(file,title,startDate,endDate,time,dayOfWeek,recurring,description);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.OK).body(GenericResponseDto.builder().error("upload image fail").build());
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.OK).body(GenericResponseDto.builder().error("date parse error").build());
        }

        return ResponseEntity.status(HttpStatus.OK).body(GenericResponseDto.builder().response("upload news").build());

    }


    @GetMapping(value="/news")
    public ResponseEntity<?> fetchAll(){
        return ResponseEntity.status(HttpStatus.OK).body(newsService.fetchAllNews());
    }

    @DeleteMapping(value = "/news/{id}")
    public ResponseEntity<?> uploadWeekly(@PathVariable Long id) {

        try {
            newsService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(GenericResponseDto.builder().response("Deleted").build());
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).body(GenericResponseDto.builder().errorCode(500).build());
        }

    }

}
