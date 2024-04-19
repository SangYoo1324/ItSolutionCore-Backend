package com.example.ItSolutionCore.common.api;

import com.example.ItSolutionCore.common.dto.BusinessDto;
import com.example.ItSolutionCore.common.dto.GenericResponseDto;
import com.example.ItSolutionCore.common.entity.Business;
import com.example.ItSolutionCore.common.exception.DataNotFoundException;
import com.example.ItSolutionCore.common.service.BusinessService;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BusinessApiController
{
    private final BusinessService businessService;


    @PostMapping("/api/business")
    public ResponseEntity<?> postBusiness(@RequestBody BusinessDto businessDto){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(businessService.createBusiness(businessDto));
        }catch(PersistenceException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GenericResponseDto.builder().error("Business name already existss").build());
        }

    }

    @GetMapping("/api/business")
    public ResponseEntity<?> getAllBusiness(){

            return ResponseEntity.status(HttpStatus.OK).body(businessService.getAllBusiness());
    }

//    @GetMapping("/api/business/{id}")
//    public ResponseEntity<?> getBusiness(@PathVariable Long id){
//    }


    @GetMapping("/api/business/users/{id}")
    public ResponseEntity<?> getAllUser(@PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(businessService.getMembersByBusiness(id));
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
