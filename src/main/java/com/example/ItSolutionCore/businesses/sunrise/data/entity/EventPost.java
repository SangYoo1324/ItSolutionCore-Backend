package com.example.ItSolutionCore.businesses.sunrise.data.entity;

import com.example.ItSolutionCore.businesses.sunrise.data.dto.EventPostDto;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Optional;

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
    private Long date;
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
//                .date(this.date.toString().substring(0,10))
                .date(date)
                .s3_url(this.image == null ? "https://sammyoopublicbucket.s3.us-west-2.amazonaws.com/post_event_replace_img.jpg" :this.image.getS3_url())
//                .time(this.time)
                .description(this.description)
                .build();
    }
}

