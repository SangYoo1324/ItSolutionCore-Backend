package com.example.ItSolutionCore.common.entityAbstract;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

/*
*
* Image class itself won't be reflected on the persistence context.
*  Just use it as a blueprint for each busiensses
*
* */

//@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name= "business_type", discriminatorType = DiscriminatorType.STRING)
//@NoArgsConstructor
//@Builder
//@AllArgsConstructor
//@Getter
//@Setter
//@Entity
public abstract class PublicZoneFile {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;


    protected String fileName;

    protected String filePath;

    protected String s3_url;

    protected String contentType;

    protected Long size;

    protected Timestamp registeredDate;

    public PublicZoneFile(String fileName, String filePath, String s3_url, String contentType, Long size, Timestamp registeredDate) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.s3_url = s3_url;
        this.contentType = contentType;
        this.size = size;
        this.registeredDate = registeredDate;
    }

}
