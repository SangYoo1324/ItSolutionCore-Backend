package com.example.ItSolutionCore.businesses.sunrise.repo;

import com.example.ItSolutionCore.businesses.sunrise.entity.Sermon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SermonRepository extends JpaRepository<Sermon, Long> {


}