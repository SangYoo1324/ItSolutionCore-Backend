package com.example.ItSolutionCore.businesses.sunrise.repo;

import com.example.ItSolutionCore.businesses.sunrise.entity.PhotoEvent;
import com.example.ItSolutionCore.businesses.sunrise.entity.SunriseFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PhotoEventRepository extends JpaRepository<PhotoEvent, Long> {

    @Query("select p from PhotoEvent p join fetch p.files where p.id = :id")
    Optional<PhotoEvent> fetchPhotoEvent(Long id);
}
