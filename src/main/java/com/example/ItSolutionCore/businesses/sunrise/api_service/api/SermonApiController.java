package com.example.ItSolutionCore.businesses.sunrise.api_service.api;


import com.example.ItSolutionCore.businesses.sunrise.data.dto.SermonDto;
import com.example.ItSolutionCore.businesses.sunrise.api_service.service.SermonService;
import com.example.ItSolutionCore.common.dto.GenericResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/public/sunrise")
public class SermonApiController {

    private final SermonService sermonService;


    @PostMapping("/sermon")
    public ResponseEntity<?> postSermon(@RequestBody SermonDto sermonDto){
        sermonService.postSermon(sermonDto);
        return ResponseEntity.status(HttpStatus.OK).body(GenericResponseDto.builder().response("200").build());

    }

    @GetMapping("/sermons")
    public ResponseEntity<?> fetchAll(){
        return ResponseEntity.status(HttpStatus.OK).body(sermonService.fetchAll());
    }
}
