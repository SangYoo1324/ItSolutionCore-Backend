package com.example.ItSolutionCore.businesses.sunrise.api_service.service;

import com.example.ItSolutionCore.businesses.sunrise.data.dto.SermonRequestDto;
import com.example.ItSolutionCore.businesses.sunrise.data.dto.SermonResponseDto;
import com.example.ItSolutionCore.businesses.sunrise.data.entity.Sermon;
import com.example.ItSolutionCore.businesses.sunrise.repo.SermonRepository;
import com.example.ItSolutionCore.common.exception.DataNotFoundException;
import com.example.ItSolutionCore.common.util.GenericUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SermonService {

    private final SermonRepository sermonRepository;

    public void postSermon(SermonRequestDto sermonRequestDto) throws ParseException {

        sermonRepository.save(Sermon.builder()
                        .date(new Timestamp(sermonRequestDto.getTimeStamp()))
                        .title(sermonRequestDto.getTitle())
                        .iframe(sermonRequestDto.getIframe())
                        .scripture(sermonRequestDto.getScripture())
                .build());

    }

    public List<SermonResponseDto> fetchAll(){
        return sermonRepository.findAll().stream().map(Sermon::toResponseDto).collect(Collectors.toList());
    }

    public void delete(Long id) throws DataNotFoundException {



        sermonRepository.delete(
                sermonRepository.findById(id).orElseThrow(()-> new DataNotFoundException("sermon not found"))
        );
    }
}
