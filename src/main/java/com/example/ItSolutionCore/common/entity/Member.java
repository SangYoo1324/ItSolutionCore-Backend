package com.example.ItSolutionCore.common.entity;


import com.example.ItSolutionCore.common.dto.MemberDto;
import com.example.ItSolutionCore.enums.Role;
import com.example.ItSolutionCore.businesses.itSolution.entity.Qna;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="member", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch= FetchType.LAZY)
    private List<Qna> qnaList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="business_id", referencedColumnName = "id")
    private Business business;

//    @Column(nullable = false)
//    private String password;

    @Enumerated(EnumType.STRING)
    private Role authority;

    private String provider;

    public MemberDto toDto(){
        return MemberDto.builder()
                .id(this.id)
                .email(this.email)
                .username(this.username)
                .business_id(this.business.getId())
                .authority(this.authority)
//                .password(this.password)
                .provider(this.provider)
                .build();
    }

}
