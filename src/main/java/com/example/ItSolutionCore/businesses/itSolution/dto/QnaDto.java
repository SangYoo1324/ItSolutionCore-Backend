package com.example.ItSolutionCore.businesses.itSolution.dto;

import com.example.ItSolutionCore.common.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

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
