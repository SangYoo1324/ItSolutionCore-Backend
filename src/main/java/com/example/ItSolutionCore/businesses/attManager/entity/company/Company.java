package com.example.ItSolutionCore.businesses.attManager.entity.company;

import com.example.ItSolutionCore.businesses.attManager.enums.Plan;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Entity
@Table(name="company")
@Builder
@Getter
@Setter
@ToString
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private Plan plan;

    private String timezone;

    @Builder
    public Company(Long id, String name, Plan plan, String timezone) {
        this.id = id;
        this.name = name;
        this.plan = plan;
        this.timezone = timezone;
    }
//    @OneToMany(mappedBy = "company", fetch =FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<User> users = new ArrayList<>();

    public CompanyResponseDto toResponseDto(){
        return CompanyResponseDto.builder()
                .id(this.id)
                .name(this.name)
                .plan(this.plan)
                .timezone(this.timezone)
                .build();
    }
}
