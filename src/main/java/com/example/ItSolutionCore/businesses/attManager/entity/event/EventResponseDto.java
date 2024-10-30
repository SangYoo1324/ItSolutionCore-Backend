package com.example.ItSolutionCore.businesses.attManager.entity.event;


import com.example.ItSolutionCore.businesses.attManager.entity.company.Company;
import com.example.ItSolutionCore.businesses.attManager.entity.company.CompanyResponseDto;
import com.example.ItSolutionCore.businesses.attManager.entity.user.User;
import com.example.ItSolutionCore.businesses.attManager.entity.user.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
@Builder
public class EventResponseDto {
    private Long id;
    private UserResponseDto employee;
    private CompanyResponseDto company;
//    private Long day; // 받아올 때 timestamp 값 1722582000000
    private Long startTime;
    private Long endTime;

    private String title;
    private String description;
}
