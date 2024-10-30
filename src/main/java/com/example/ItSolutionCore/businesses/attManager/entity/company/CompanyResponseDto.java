package com.example.ItSolutionCore.businesses.attManager.entity.company;

import com.example.ItSolutionCore.businesses.attManager.entity.user.User;
import com.example.ItSolutionCore.businesses.attManager.enums.Plan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
@Builder
public class CompanyResponseDto {

    private Long id;
    private String name;
    private Plan plan;
    private List<User> users = new ArrayList<>();

    private String timezone;

}
