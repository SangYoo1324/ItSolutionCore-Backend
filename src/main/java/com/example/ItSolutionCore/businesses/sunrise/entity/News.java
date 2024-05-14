package com.example.ItSolutionCore.businesses.sunrise.entity;

import com.example.ItSolutionCore.businesses.sunrise.dto.NewsDto;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private Timestamp startDate;
    @Column
    private Timestamp endDate;
    @Column
    String dayOfWeek;

    @Column
    String time;
    @Column
    boolean recurring;
    @Column
    private String cloudinaryUrl;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    public NewsDto toNewsDto(){
        return NewsDto.builder()
                .id(this.id)
                .title(this.title)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .dayOfWeek(this.dayOfWeek)
                .recurring(this.recurring)
                .cloudinaryUrl(this.cloudinaryUrl)
                .description(this.description)
                .time(this.time)
                .build();
    }
}
