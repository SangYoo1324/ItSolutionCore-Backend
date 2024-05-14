package com.example.ItSolutionCore.common.service;


import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.ItSolutionCore.common.config.AWSConfig;
import com.example.ItSolutionCore.common.uni_dto.PublicFileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private final AWSConfig s3Config;

    @Value("${s3.local.path}")
    private String localLocation;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public PublicFileDto imageUpload(MultipartFile file, String business, String category) throws IOException {
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.indexOf("."));
        log.info("File Extention = "+ ext);

        // filename will be like businessName_photoEvent_123jl1eio2.jpeg
        String uuidFileName = business+"_"+category+"_"+UUID.randomUUID()+ ext;
        String localPath = localLocation+ uuidFileName;

        // transfer Multipart file to local file
        File localFile = new File(localPath);
        file.transferTo(localFile);

        s3Config.amazonS3Client()               //              key         object
                .putObject(new PutObjectRequest(bucketName, uuidFileName, localFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

        String s3Url = s3Config.amazonS3Client().getUrl(bucketName, uuidFileName).toString();

        localFile.delete();

        return PublicFileDto.builder()
                .fileName(uuidFileName)
                .s3_url(s3Url)
                .contentType(ext)
                .size(file.getSize()) // byte
                .filePath(localPath) // stores the local lcation size to determine it's uploaded from dev mode OR prod mode
                .registeredDate(Timestamp.valueOf(LocalDateTime.now()))
                .business(business)
                .build();
    }
}
