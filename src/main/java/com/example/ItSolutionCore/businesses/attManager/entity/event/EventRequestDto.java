package com.example.ItSolutionCore.businesses.attManager.entity.event;

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
public class EventRequestDto {

    private Long id;
    private Long user_id;
    private Long company_id;
    private Long day; // 받아올 때 timestamp 값 1722582000000
    private Long startTime;
    private Long endTime;

    private String title;
    private String description;
}
