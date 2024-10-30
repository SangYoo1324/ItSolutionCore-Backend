package com.example.ItSolutionCore.businesses.attManager.repo;

import com.example.ItSolutionCore.businesses.attManager.entity.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("select e from Event e left join e.company c where c.id=:id")
    List<Event> findAllByCompany(Long id);

    @Query("select e from Event e left join e.user u where u.id=:id")
    List<Event> findAllByUser(Long id);
}
