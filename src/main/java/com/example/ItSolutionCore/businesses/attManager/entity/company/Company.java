package com.example.ItSolutionCore.businesses.attManager.entity;

import com.example.ItSolutionCore.businesses.attManager.entity.user.User;
import com.example.ItSolutionCore.businesses.attManager.enums.Plan;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.AnyKeyJavaClass;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="company")
@Builder
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private Plan plan;

    @OneToMany(mappedBy = "company", fetch =FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();
}
