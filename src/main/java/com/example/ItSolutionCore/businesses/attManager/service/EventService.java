package com.example.ItSolutionCore.businesses.attManager.service;

import com.example.ItSolutionCore.businesses.attManager.entity.event.Event;
import com.example.ItSolutionCore.businesses.attManager.entity.event.EventRequestDto;
import com.example.ItSolutionCore.businesses.attManager.entity.event.EventResponseDto;
import com.example.ItSolutionCore.businesses.attManager.entity.user.User;
import com.example.ItSolutionCore.businesses.attManager.entity.user.employee.Employee;
import com.example.ItSolutionCore.businesses.attManager.repo.CompanyRepository;
import com.example.ItSolutionCore.businesses.attManager.repo.EmployeeRepository;
import com.example.ItSolutionCore.businesses.attManager.repo.EventRepository;
import com.example.ItSolutionCore.common.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.DateTimeException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(transactionManager = "attManagerTransactionManager")
@RequiredArgsConstructor
public class EventService {

    private final EmployeeRepository employeeRepository;
    private final EventRepository eventRepository;
    private final CompanyRepository companyRepository;

    public EventResponseDto postEvent(EventRequestDto eventRequestDto) throws DataNotFoundException {

        // 1. get Employee entity
        Employee targetEmp = employeeRepository.findById(eventRequestDto.getUser_id())
                .orElseThrow(()-> new DataNotFoundException("Cannot find employee related to the event. USER_ID::"+ eventRequestDto.getUser_id()));

        // 2. form Event entity
       return eventRepository.save( Event.builder()
//                .day(new Timestamp(eventRequestDto.getDay()))
                .title(eventRequestDto.getTitle())
                .description(eventRequestDto.getDescription())
                .startTime(eventRequestDto.getStartTime())
                .endTime(eventRequestDto.getEndTime())
                .user(targetEmp)
                .company(targetEmp.getCompany())
                .build()
        ).toResponseDto();


    }

    public List<EventResponseDto> fetchAllEvent_company(Long company_id){

        return eventRepository.findAllByCompany(company_id).stream().map(Event::toResponseDto).collect(Collectors.toList());
    }

    public List<EventResponseDto> fetchAllEvent_emp(Long emp_id){
        return eventRepository.findAllByUser(emp_id).stream().map(Event::toResponseDto).collect(Collectors.toList());
    }

    public Event updateEvent(EventRequestDto dto) throws DataNotFoundException {

        Event event = eventRepository.findById(dto.getId())
                .orElseThrow(()->new DataNotFoundException("updateEvent: Event Not found by eventId"));
        User user = employeeRepository.findById(dto.getUser_id()).orElseThrow(()->new DataNotFoundException("User not found by eventDto's user_id"));
        // dto field update
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setUser(user);
        event.setStartTime(dto.getStartTime());
        event.setEndTime(dto.getEndTime());

        return eventRepository.save(event);
    }

    public void deleteEvent(Long event_id) throws DataNotFoundException {

        eventRepository.delete(eventRepository.findById(event_id).orElseThrow(()->new DataNotFoundException("cannot find event related to provided event_id")));
    }


}
