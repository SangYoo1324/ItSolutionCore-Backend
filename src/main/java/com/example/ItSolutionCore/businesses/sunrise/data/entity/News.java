package com.example.ItSolutionCore.businesses.sunrise.data.entity;

import com.example.ItSolutionCore.businesses.sunrise.data.dto.NewsDto;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    private Long startDate;
    @Column
    private Long endDate;
    @Column
    String dayOfWeek;

    @Column
    String time;
    @Column
    boolean recurring;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    //참조 당하는쪽이라 부모   SunriseFile이 자식
    @OneToMany(mappedBy ="news", fetch = FetchType.LAZY)
    private List<SunriseFile> sunriseFiles = new ArrayList<>();

    public NewsDto toNewsDto(){
        return NewsDto.builder()
                .id(this.id)
                .title(this.title)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .dayOfWeek(this.dayOfWeek)
                .recurring(this.recurring)
                .description(this.description)
                .time(this.time)
                .build();
    }
}
