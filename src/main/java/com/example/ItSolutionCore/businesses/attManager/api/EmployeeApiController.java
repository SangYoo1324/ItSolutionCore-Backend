package com.example.ItSolutionCore.businesses.attManager.api;

import com.example.ItSolutionCore.businesses.attManager.entity.user.employee.EmployeeRequestDto;
import com.example.ItSolutionCore.businesses.attManager.service.EmployeeService;
import com.example.ItSolutionCore.common.dto.GenericResponseDto;
import com.example.ItSolutionCore.common.exception.DataNotFoundException;
import com.example.ItSolutionCore.common.exception.DupeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/employee")
@Slf4j
public class EmployeeApiController {
    private final EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeRequestDto employeeRequestDto){
        log.info(employeeRequestDto.toString());
        ObjectMapper objectMapper = new ObjectMapper();
//        EmployeeRequestDto mappedEmpDto = null;
//        try {
//            mappedEmpDto = objectMapper.readValue(employeeRequestDto, EmployeeRequestDto.class);
//            log.info(mappedEmpDto.toString());
//        } catch (JsonProcessingException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GenericResponseDto.builder().error(null));
//        }

        try {
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.addEmployee(employeeRequestDto));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GenericResponseDto.builder().error("Data not Found"));
        } catch (DupeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GenericResponseDto.builder().error("Dupe User"));
        }
    }


    @GetMapping("/fetch/{id}")
    public ResponseEntity<?> fetchAllEmployeesBy_CompanyId(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.fetchAllEmployeesBy_CompanyId(id));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GenericResponseDto.builder().error("data not found"));
        }
    }
}
