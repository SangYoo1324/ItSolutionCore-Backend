package com.example.ItSolutionCore.businesses.sunrise.entity;

import com.example.ItSolutionCore.common.entityAbstract.PublicZoneFile;
import com.example.ItSolutionCore.common.uni_dto.PublicFileDto;
import jakarta.persistence.*;
import jdk.jfr.Event;
import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sunrise_file")
public class SunriseFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fileName;
    @Column
    private String filePath;
    @Column
    private String s3_url;
    @Column
    private String contentType;
    @Column
    private Long size;
    @Column
    private Timestamp registeredDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="photoEvent_id", referencedColumnName = "id")
    private PhotoEvent photoEvent;


    public SunriseFile(String fileName, String filePath, String s3_url, String contentType, Long size, Timestamp registeredDate) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.s3_url = s3_url;
        this.contentType = contentType;
        this.size = size;
        this.registeredDate = registeredDate;
    }

    public PublicFileDto toPublicFileDto(){
        return PublicFileDto
                .builder()
                .id(this.id)
                .fileName(this.fileName)
                .filePath(this.filePath)
                .s3_url(this.s3_url)
                .size(this.size)
                .contentType(this.contentType)
                .registeredDate(this.registeredDate)
                .build();
    }

}
