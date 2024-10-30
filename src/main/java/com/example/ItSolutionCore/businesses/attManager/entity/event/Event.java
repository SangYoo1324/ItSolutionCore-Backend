package com.example.ItSolutionCore.businesses.attManager.entity.event;

import com.example.ItSolutionCore.businesses.attManager.entity.company.Company;
import com.example.ItSolutionCore.businesses.attManager.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName="id", nullable=true)
    private User user;

    @ManyToOne
    @JoinColumn(name="company_id", referencedColumnName = "id", nullable = false)
    private Company company;

//    private Timestamp day;


    private Long startTime;
    private Long endTime;

    private String title;
    private String description;


    public EventResponseDto toResponseDto() {
        return EventResponseDto.builder()
                .id(this.id)
                .company(this.getCompany().toResponseDto())
                .employee(this.getUser().toResponseDto())
//              .day(this.getDay().getTime())
                .title(this.getTitle())
                .description(this.getDescription())
                .startTime(this.getStartTime())
                .endTime(this.getEndTime())
                .build();
    }
}
