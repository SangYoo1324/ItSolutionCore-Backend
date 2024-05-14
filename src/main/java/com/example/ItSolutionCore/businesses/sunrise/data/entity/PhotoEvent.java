package com.example.ItSolutionCore.businesses.sunrise.data.entity;

import com.example.ItSolutionCore.businesses.sunrise.data.dto.PhotoEventDto;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "photo_event")
public class PhotoEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;
    @Column
    private String subTitle;
    @Column
    private Timestamp date;

    @OneToMany(mappedBy = "photoEvent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SunriseFile> files = new ArrayList<>();



    public PhotoEventDto toDto(){
        return PhotoEventDto.builder()
                .id(this.id)
                .s3_urls(this.files.size() == 0 ? null : this.files.stream().map(SunriseFile::getS3_url).collect(Collectors.toList()))
                .subTitle(this.subTitle)
                .title(this.title)
                .build();
    }

}
