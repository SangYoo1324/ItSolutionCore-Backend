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
import java.time.DayOfWeek;
import java.time.LocalDate;
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

    public void postRegularEvent(String title, String date, String time, String description, MultipartFile multipartFile) throws IOException, DataNotFoundException {
        log.info("date String received from client"+ date);
        // change Date format
        long timeStamp = 0;

        try {
            timeStamp = GenericUtil.convertToTimeStamp(date);
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("cannot parse given String "+date+"into timeStamp");
        }

        // make EventPost



        // Upload image with eventPost + relate img to eventPost
          SunriseFile imageEntity = sunrisePublicFileService.upload(multipartFile, BusinessVars_sunrise.EVENT_POST);

        EventPost eventPost =  eventPostRepository.save(EventPost.builder()
                .title(title)
                .date(new Timestamp(timeStamp))
                .description(description)
                .time(time)
                .image(imageEntity)
                .build());

//          sunriseEntityManager.flush();
          log.info("eventPost ID:: "+ eventPost.getId());
          log.info("img ID::"+imageEntity.getId());

    }

    public void postWeeklyEvent(String title, String date, String time, String description, MultipartFile multipartFile) throws IOException {

        // Upload image with eventPost + relate img to eventPost
        SunriseFile imageEntity = sunrisePublicFileService.upload(multipartFile, BusinessVars_sunrise.EVENT_POST);

        log.info("date String received from client"+ date);
        // change Date format
        long timeStamp = 0;

        try {
            // still long type
            timeStamp = GenericUtil.convertToTimeStamp(date);
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("cannot parse given String "+date+"into timeStamp");
        }

        // long to timestamp Obj
        Timestamp timestampObj = new Timestamp(timeStamp);
        //Timestamp to localDate
        LocalDate  localDate = timestampObj.toLocalDateTime().toLocalDate();

        // 요일  (WEDNESDAY)
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        log.info("given Date's DayOfWeek:: "+ dayOfWeek);

        // Getting first & last date of the month
        // withDayOfMonth =  localDate's month's certain date(1, 30 15, etc...)
        LocalDate firsOfMonth = localDate.withDayOfMonth(1);
        LocalDate lastOfMonth = localDate.withDayOfMonth(localDate.lengthOfMonth());
        log.info("First date of the month : "+firsOfMonth+ " / Last date of the month: "+ lastOfMonth);
        // getting month's first date of 요일 (ex: 달의 첫째 주 n요일)
        LocalDate firstDateOfDayOfWeek = firsOfMonth.with(TemporalAdjusters.nextOrSame(dayOfWeek));
        log.info("First Date of" + dayOfWeek+" = "+ firstDateOfDayOfWeek);

        while(!firstDateOfDayOfWeek.isAfter(lastOfMonth)){
            log.info("adding event to "+Timestamp.valueOf(firstDateOfDayOfWeek.atStartOfDay()));

            EventPost eventPost =  eventPostRepository.save(EventPost.builder()
                    .title(title)
                    .date(Timestamp.valueOf(firstDateOfDayOfWeek.atStartOfDay()))
                    .description(description)
                    .time(time)
                    .image(imageEntity)
                    .build());

            firstDateOfDayOfWeek = firstDateOfDayOfWeek.plusWeeks(1);
            log.info("weekly recurring event "+ Timestamp.valueOf(firstDateOfDayOfWeek.atStartOfDay()));
        }



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
