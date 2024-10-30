package com.example.ItSolutionCore.businesses.attManager.api;


import com.example.ItSolutionCore.businesses.attManager.entity.company.CompanyRequestDto;
import com.example.ItSolutionCore.businesses.attManager.service.CompanyService;
import com.example.ItSolutionCore.common.dto.GenericResponseDto;
import com.example.ItSolutionCore.common.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/company")
@Slf4j
public class CompanyApiController {

    private final CompanyService companyService;

    @PostMapping("/create")
    public ResponseEntity<?> createCompany(@RequestBody CompanyRequestDto companyRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(companyService.createCompany(companyRequestDto));
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<?> fetchCompanyById(@PathVariable Long id){
        log.info("api id:: {}",id);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(companyService.fetchCompany(id));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GenericResponseDto.builder().response("error")
                    .errorCode(500).build());
        }
    }

    @GetMapping("/fetch/all")
    public ResponseEntity<?> fetchCompanies(){
        return ResponseEntity.status(HttpStatus.OK).body(companyService.fetchAllCompany());
    }
}
