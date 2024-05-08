package com.example.ItSolutionCore.businesses.sunrise.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
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



}

