package com.example.ItSolutionCore.common.dto;

import com.example.ItSolutionCore.common.entity.Business;
import com.example.ItSolutionCore.common.entity.Member;
import com.example.ItSolutionCore.enums.PlanType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@Data
public class BusinessDto {

    private Long id;

    private String name;

    private PlanType planType;

    private List<Member> memberList;

}
