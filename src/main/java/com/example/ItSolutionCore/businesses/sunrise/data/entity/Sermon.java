package com.example.ItSolutionCore.businesses.sunrise.data.entity;

import com.example.ItSolutionCore.businesses.sunrise.data.dto.SermonDto;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="sermon")
public class Sermon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String iframe;
    @Column
    private String title;
    @Column
    private Timestamp date;
    @Lob
    private String scripture;


    @Builder
    public Sermon(Long id, String iframe, String title, Timestamp date, String scripture) {
        this.id = id;
        this.iframe = iframe;
        this.title = title;
        this.date = date;
        this.scripture = scripture;
    }

    public SermonDto toDto(){
        return SermonDto.builder()
                .id(this.id)
                .date(this.date.getTime())
                .iframe(this.iframe)
                .scripture(this.scripture)
                .title(this.title)
                .build();
    }



}

