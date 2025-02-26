package com.example.ItSolutionCore.businesses.sunrise.api_service.service;


import com.example.ItSolutionCore.businesses.sunrise.BusinessVars_sunrise;
import com.example.ItSolutionCore.businesses.sunrise.data.dto.EventPostDto;
import com.example.ItSolutionCore.businesses.sunrise.data.entity.EventPost;
import com.example.ItSolutionCore.businesses.sunrise.data.entity.SunriseFile;
import com.example.ItSolutionCore.businesses.sunrise.repo.EventPostRepository;
import com.example.ItSolutionCore.common.exception.DataNotFoundException;
import com.example.ItSolutionCore.common.service.S3Service;
import com.example.ItSolutionCore.common.util.GenericUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@Slf4j
public class EventPostService {


    private final EventPostRepository eventPostRepository;

    private final SunrisePublicFileService sunrisePublicFileService;

    @PersistenceContext(unitName = "sunriseEntityManager")
    private EntityManager sunriseEntityManager;


    public EventPostService( EventPostRepository eventPostRepository, SunrisePublicFileService sunrisePublicFileService) {
        this.eventPostRepository = eventPostRepository;
        this.sunrisePublicFileService = sunrisePublicFileService;

    }

    public void postRegularEvent(String title, Long timeStamp, String time, String description, MultipartFile multipartFile) throws IOException, DataNotFoundException {

        // Upload image with eventPost + relate img to eventPost
          SunriseFile imageEntity = sunrisePublicFileService.upload(multipartFile, BusinessVars_sunrise.EVENT_POST);

        EventPost eventPost =  eventPostRepository.save(EventPost.builder()
                .title(title)
                .date(timeStamp)
                .description(description)
                .time(time)
                .image(imageEntity)
                .build());

//          sunriseEntityManager.flush();
          log.info("eventPost ID:: "+ eventPost.getId());
          log.info("img ID::"+imageEntity.getId());

    }

    public void postWeeklyEvent(String title, Long timeStamp, String time, String description, MultipartFile multipartFile, String timeZone) throws IOException {

        // Upload image with eventPost + relate img to eventPost
        SunriseFile imageEntity = sunrisePublicFileService.upload(multipartFile, BusinessVars_sunrise.EVENT_POST);

        log.info("date Long value received from client"+ timeStamp);

        ZoneId zoneId = ZoneId.of(timeZone);

        // timeZone에 맞춰서 LocalDateTime으로 변환
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(timeStamp),zoneId);
        LocalDate localDate = zonedDateTime.toLocalDate();// 해당 타임존에 맞는 날짜를 구함
//        LocalDateTime startOfMonth = localDate.withDayOfMonth(1).atStartOfDay();// 해당 월의 첫 날
        LocalDateTime startDate = localDate.atStartOfDay();
        LocalDateTime endOfMonth = localDate.withDayOfMonth(localDate.lengthOfMonth()).atStartOfDay(); //해당 월의 마지막 날

        //시작 날짜로부터 1주일 씩 더하며 반복
        LocalDateTime eventDate = startDate;
        while(!eventDate.isAfter(endOfMonth)){
            // 이벤트 저장
            EventPost eventPost = eventPostRepository.save(EventPost.builder()
                    .title(title)
                    .date(Timestamp.valueOf(eventDate).getTime())  // Date가 밀리초로 저장됨
                    .description(description)
                    .time(time)
                    .image(imageEntity)
                    .build());

            // 1주일 더하기
            eventDate = eventDate.plusWeeks(1);

            // 로그 추가
            log.info("Created Event for " + eventDate);
        }


//        // long to timestamp Obj
//        Timestamp timestampObj = new Timestamp(timeStamp);
//        //Timestamp to localDate
//        LocalDate  localDate = timestampObj.toLocalDateTime().toLocalDate();
//
//

//        // 요일  (WEDNESDAY)
//        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
//        log.info("given Date's DayOfWeek:: "+ dayOfWeek);
//
//        // Getting first & last date of the month
//        // withDayOfMonth =  localDate's month's certain date(1, 30 15, etc...)
//        LocalDate firsOfMonth = localDate.withDayOfMonth(1);
//        LocalDate lastOfMonth = localDate.withDayOfMonth(localDate.lengthOfMonth());
//        log.info("First date of the month : "+firsOfMonth+ " / Last date of the month: "+ lastOfMonth);
//        // getting month's first date of 요일 (ex: 달의 첫째 주 n요일)
//        LocalDate firstDateOfDayOfWeek = firsOfMonth.with(TemporalAdjusters.nextOrSame(dayOfWeek));
//        log.info("First Date of" + dayOfWeek+" = "+ firstDateOfDayOfWeek);
//
//        while(!firstDateOfDayOfWeek.isAfter(lastOfMonth)){
//
//            EventPost eventPost =  eventPostRepository.save(EventPost.builder()
//                    .title(title)
//                    .date(Timestamp.valueOf(firstDateOfDayOfWeek.atStartOfDay()).getTime())
//                    .description(description)
//                    .time(time)
//                    .image(imageEntity)
//                    .build());
//
//            firstDateOfDayOfWeek = firstDateOfDayOfWeek.plusWeeks(1);
//            log.info("weekly recurring event "+ Timestamp.valueOf(firstDateOfDayOfWeek.atStartOfDay()));
//        }



    }


    public List<EventPostDto> fetchAll(){
        return   eventPostRepository.findAll_join_img().stream().map(EventPost::toEventPostDto).collect(Collectors.toList());
    }



    public void delete(Long id) throws DataNotFoundException {



        eventPostRepository.delete(
                eventPostRepository.findById(id).orElseThrow(()-> new DataNotFoundException("eventPost not found"))
        );
    }



}
