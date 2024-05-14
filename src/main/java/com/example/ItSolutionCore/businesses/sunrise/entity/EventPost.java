package com.example.ItSolutionCore.businesses.sunrise.entity;

import com.example.ItSolutionCore.businesses.sunrise.dto.EventPostDto;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="event_post")
public class EventPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private Timestamp date;
    @Column
    private String time;
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name= "sunrise_file_id", referencedColumnName =  "id")
    private SunriseFile image;
    @Column
    private String description;

    public EventPostDto toEventPostDto(){


        return EventPostDto.builder()
                .id(this.id)
                .title(this.title)
                .date(this.date.toString().substring(0,10))
                .s3_url(this.image.getS3_url())
                .time(this.time)
                .description(this.description)
                .build();
    }
}

