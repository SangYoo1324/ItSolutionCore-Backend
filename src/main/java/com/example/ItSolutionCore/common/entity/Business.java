package com.example.ItSolutionCore.common.entity;


import com.example.ItSolutionCore.common.auth.entity.Member;
import com.example.ItSolutionCore.common.dto.BusinessDto;
import com.example.ItSolutionCore.enums.PlanType;
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
@Table(name="business", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanType planType;

    @OneToMany(mappedBy = "business",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Member> memberList = new ArrayList<>();


    public BusinessDto toDto(){
        return BusinessDto.builder()
                .id(this.id)
                .name(this.name)
                .planType(this.planType)
                .memberList(this.memberList)
                .build();
    }
}
