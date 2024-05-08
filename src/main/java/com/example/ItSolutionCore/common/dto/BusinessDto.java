package com.example.ItSolutionCore.common.dto;

import com.example.ItSolutionCore.common.auth.entity.Member;
import com.example.ItSolutionCore.enums.PlanType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class BusinessDto {

    private Long id;

    private String name;

    private PlanType planType;

    private List<Member> memberList;

}
