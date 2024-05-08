package com.example.ItSolutionCore.common.auth.dto;

import com.example.ItSolutionCore.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthMemberDto {

    private Role role;
    private String name;
    private String provider;
    private String username;
    private String password;
    private String email;
    private String business;

}
