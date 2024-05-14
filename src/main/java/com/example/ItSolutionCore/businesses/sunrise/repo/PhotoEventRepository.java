package com.example.ItSolutionCore.businesses.sunrise.repo;

import com.example.ItSolutionCore.businesses.sunrise.data.entity.PhotoEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PhotoEventRepository extends JpaRepository<PhotoEvent, Long> {

    @Query("select p from PhotoEvent p join fetch p.files where p.id = :id")
    Optional<PhotoEvent> fetchPhotoEvent(Long id);

    @Query("select  p from PhotoEvent p join fetch  p.files")
    List<PhotoEvent> findAll_with_img();
}
