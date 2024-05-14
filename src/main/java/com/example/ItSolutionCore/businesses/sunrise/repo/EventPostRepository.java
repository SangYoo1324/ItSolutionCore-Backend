package com.example.ItSolutionCore.businesses.sunrise.repo;

import com.example.ItSolutionCore.businesses.sunrise.data.entity.EventPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventPostRepository extends JpaRepository<EventPost, Long> {

//    @Query("select e from EventPost e join fetch e.image where e.image.id = :id")
//    Optional<EventPost> fetchPostByImgId(Long id);
//
//    @Query("select e from EventPost e join fetch e.image where e.id = :id")
//    Optional<EventPost> fetchById_join_img(Long id);

    @Query("select e from EventPost e join fetch e.image")
    List<EventPost> findAll_join_img();
}
