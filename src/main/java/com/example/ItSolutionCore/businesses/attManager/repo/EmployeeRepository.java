package com.example.ItSolutionCore.businesses.attManager.repo;

import com.example.ItSolutionCore.businesses.attManager.entity.user.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("select e from Employee e where e.company.id = :id")
    Optional<List<Employee>> findAllEmployeeBy_CompanyId(Long id);

    @Query("select MAX(e.employeeID) from Employee e")
   Long findMostRecentEmployeeId();
}
