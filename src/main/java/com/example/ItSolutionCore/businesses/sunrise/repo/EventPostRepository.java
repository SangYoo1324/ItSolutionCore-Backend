package com.example.ItSolutionCore.businesses.sunrise.repo;

import com.example.ItSolutionCore.businesses.sunrise.entity.EventPost;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface EventPostRepository extends JpaRepository<EventPost, Long> {

//    @Query("select e from EventPost e join fetch e.image where e.image.id = :id")
//    Optional<EventPost> fetchPostByImgId(Long id);
//
//    @Query("select e from EventPost e join fetch e.image where e.id = :id")
//    Optional<EventPost> fetchById_join_img(Long id);

    @Query("select e from EventPost e join fetch e.image")
    List<EventPost> findAll_join_img();
}
