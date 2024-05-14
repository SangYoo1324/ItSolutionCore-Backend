package com.example.ItSolutionCore.businesses.sunrise.api_service.service;

import com.example.ItSolutionCore.businesses.sunrise.BusinessVars_sunrise;
import com.example.ItSolutionCore.businesses.sunrise.data.dto.NewsDto;
import com.example.ItSolutionCore.businesses.sunrise.data.entity.News;
import com.example.ItSolutionCore.businesses.sunrise.repo.NewsRepository;
import com.example.ItSolutionCore.common.util.GenericUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class NewsService {
    private final NewsRepository newsRepository;
    private final SunrisePublicFileService sunrisePublicFileService;

    public void postNews(MultipartFile file, String title, String startDate, String endDate, String time, String dayOfWeek, boolean recurring, String description) throws IOException, ParseException {



        // generate newsEntity
        News news = newsRepository.save(
                News.builder()
                        .title(title)
                        .startDate( new Timestamp(GenericUtil.convertToTimeStamp(startDate)))
                        .endDate(new Timestamp(GenericUtil.convertToTimeStamp(endDate)))
                        .time(time)
                        .dayOfWeek(dayOfWeek)
                        .recurring(recurring)
                        .description(description)
                        .build()
        );

        // upload image
        sunrisePublicFileService.upload(file, BusinessVars_sunrise.NEWS, news);


    }


    public List<NewsDto> fetchAllEvents(){
       return  newsRepository.fetchAllNewsWithImage()
               .stream().map(n->{
                  String s3_url = n.getSunriseFiles().get(0).getS3_url();

                  NewsDto newsDto = n.toNewsDto();
                  newsDto.setS3_url(s3_url);
                   return newsDto;
               }).collect(Collectors.toList());
    }
}
