package com.example.ItSolutionCore.businesses.sunrise.api_service.service;

import com.example.ItSolutionCore.businesses.sunrise.BusinessVars_sunrise;
import com.example.ItSolutionCore.businesses.sunrise.data.dto.NewsDto;
import com.example.ItSolutionCore.businesses.sunrise.data.entity.News;
import com.example.ItSolutionCore.businesses.sunrise.data.entity.SunriseFile;
import com.example.ItSolutionCore.businesses.sunrise.repo.NewsRepository;
import com.example.ItSolutionCore.businesses.sunrise.repo.SunriseFileRepository;
import com.example.ItSolutionCore.common.exception.DataNotFoundException;
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

    private final SunriseFileRepository sunriseFileRepository;

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


    public List<NewsDto> fetchAllNews(){
       return
//               newsRepository.findAll().stream().map(News::toNewsDto).collect(Collectors.toList());

               newsRepository.fetchAllNewsWithImage()
               .stream().map(n->{
                   log.info("newsEntity: {}" , n.getSunriseFiles());
                  String s3_url = n.getSunriseFiles().get(0).getS3_url();
                    log.info("news:"+ n.getTitle());
                    log.info("s3_url"+ s3_url);
                  NewsDto newsDto = n.toNewsDto();
                  newsDto.setS3_url(s3_url);
                   return newsDto;
               }).collect(Collectors.toList());
    }

    public void delete(Long id) throws DataNotFoundException {
        News target =  newsRepository.findByIdWithImage(id).orElseThrow(()-> new DataNotFoundException("news not found"));
        News targetWithoutImage =  newsRepository.findById(id).orElseThrow(()-> new DataNotFoundException("news not found"));
        // NewsService is not an owner of the relationship. So, need to delete corresponding SunriseFile First.
        SunriseFile targetImage = target.getSunriseFiles().get(0);
        newsRepository.save(target);
        sunrisePublicFileService.delete(targetImage.getId());

        newsRepository.delete(targetWithoutImage);
    }
}
