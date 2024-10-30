package com.example.ItSolutionCore.businesses.attManager.api;

import com.example.ItSolutionCore.businesses.attManager.entity.event.EventRequestDto;
import com.example.ItSolutionCore.businesses.attManager.service.EventService;
import com.example.ItSolutionCore.common.dto.GenericResponseDto;
import com.example.ItSolutionCore.common.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/event")
@Slf4j
public class EventApiController {

    private final EventService eventService;

    @PostMapping("/create")
    public ResponseEntity<?> postEvent(@RequestBody EventRequestDto eventRequestDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(eventService.postEvent(eventRequestDto));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GenericResponseDto.builder().error("failed to post event"));
        }
    }

    @GetMapping("/fetch/company/{id}")
    public ResponseEntity<?> fetchBy_company(@PathVariable Long id) {

            return ResponseEntity.status(HttpStatus.OK).body(eventService.fetchAllEvent_company(id));

    }

    @GetMapping("/fetch/employee/{id}")
    public ResponseEntity<?> fetchBy_emp(@PathVariable Long id) {

            return ResponseEntity.status(HttpStatus.OK).body(eventService.fetchAllEvent_emp(id));

    }

    @PatchMapping("/patch")
    public ResponseEntity<?> update_event(@RequestBody EventRequestDto eventRequestDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(eventService.updateEvent(eventRequestDto));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GenericResponseDto.builder().error("failed to update event").build());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete_event(@PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.status(HttpStatus.OK).body(GenericResponseDto.builder().response("Successfully deleted..").build());
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GenericResponseDto.builder().error("failed to fetch event").build());
        }
    }
}
