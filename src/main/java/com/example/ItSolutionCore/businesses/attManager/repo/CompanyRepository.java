package com.example.ItSolutionCore.businesses.attManager.repo;

import com.example.ItSolutionCore.businesses.attManager.entity.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

//    @Query("select c from Company c left join fetch c.users where c.id = :id")
//    Optional<Company> findByCompany_id_with_users(Long id);
    @Query("select c from Company c where c.id= :id")
    Optional<Company> findById(Long id);
}
