package com.example.ItSolutionCore.common.dto;

import com.example.ItSolutionCore.common.entity.Business;
import com.example.ItSolutionCore.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Data
@AllArgsConstructor
@Slf4j
@Builder
public class MemberDto {

    private Long id;

    private String username;
    private String name;

    private String email;

    private Long business_id;

    private String password;

    private Role authority;

    private String provider;
    private boolean verified;
}
