package com.example.ItSolutionCore.businesses.sunrise.api_service.service;

import com.example.ItSolutionCore.businesses.sunrise.data.dto.SermonDto;
import com.example.ItSolutionCore.businesses.sunrise.data.entity.Sermon;
import com.example.ItSolutionCore.businesses.sunrise.repo.SermonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SermonService {

    private final SermonRepository sermonRepository;

    public void postSermon(SermonDto sermonDto){

        sermonRepository.save(Sermon.builder()
                        .date(sermonDto.getDate())
                        .title(sermonDto.getTitle())
                        .iframe(sermonDto.getIframe())
                        .scripture(sermonDto.getScripture())
                .build());

    }

    public List<SermonDto> fetchAll(){
        return sermonRepository.findAll().stream().map(Sermon::toDto).collect(Collectors.toList());
    }

}
