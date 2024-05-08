package com.example.ItSolutionCore.common.auth.entity;


import com.example.ItSolutionCore.common.auth.dto.AuthMemberDto;
import com.example.ItSolutionCore.common.dto.MemberDto;
import com.example.ItSolutionCore.common.entity.Business;
import com.example.ItSolutionCore.enums.Role;
import com.example.ItSolutionCore.businesses.itSolution.entity.Qna;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="member", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String username;

    private String email;

//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch= FetchType.LAZY)
//    private List<Qna> qnaList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="business_id", referencedColumnName = "id")
    private Business business;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    private Role authority;


    private String provider;

    @Column(columnDefinition = "boolean default false")
    private boolean verified;

    public MemberDto toDto(){
        return MemberDto.builder()
                .id(this.id)
                .email(this.email)
                .name(this.name)
                .username(this.username)
                .business_id(this.business !=null ? this.business.getId() : null)
                .authority(this.authority)
                .password(this.password)
                .provider(this.provider)
                .verified(this.verified)
                .build();
    }

    public AuthMemberDto toAuthMemberDto(){
        return AuthMemberDto.builder()
                .email(this.email)
                .username(this.username)
                .name(this.name)
                .role(this.authority)
                .password(this.password)
                .provider(this.provider)
                .build();
    }


}
