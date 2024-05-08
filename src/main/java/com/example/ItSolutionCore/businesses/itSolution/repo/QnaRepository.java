package com.example.ItSolutionCore.businesses.itSolution.repo;

import com.example.ItSolutionCore.businesses.itSolution.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Long> {

//    @Query("SELECT q from Qna q JOIN FETCH Member m ON q.member.id = m.id")
//    List<Qna> fetchAll();
}
