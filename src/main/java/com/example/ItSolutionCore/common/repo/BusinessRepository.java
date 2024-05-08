package com.example.ItSolutionCore.common.repo;


import com.example.ItSolutionCore.common.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
    @Query("select b from Business b where b.name = :businessName")
    Optional<Business> findByName_onlyBusiness(String businessName);
}
