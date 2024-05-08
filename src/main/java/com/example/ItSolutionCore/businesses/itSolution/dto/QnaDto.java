package com.example.ItSolutionCore.businesses.itSolution.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class QnaDto {

    private Long id;


    private Long member_id;

    private String title;

    private String content;

    private String date;

}
