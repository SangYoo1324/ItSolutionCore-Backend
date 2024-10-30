package com.example.ItSolutionCore.businesses.attManager.entity.event;

import com.example.ItSolutionCore.businesses.attManager.entity.company.Company;
import com.example.ItSolutionCore.businesses.attManager.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
@Builder
public class EventDto {

    private Long id;
    private Long user_id;
    private Long company_id;
    private Long day; // 받아올 때 timestamp 값 1722582000000
    private Long startTime;
    private Long endTime;

    private String title;
    private String description;
}
