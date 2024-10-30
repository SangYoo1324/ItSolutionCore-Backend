package com.example.ItSolutionCore.businesses.attManager.service;


import com.example.ItSolutionCore.businesses.attManager.entity.company.Company;
import com.example.ItSolutionCore.businesses.attManager.entity.user.User;
import com.example.ItSolutionCore.businesses.attManager.entity.user.UserRequestDto;
import com.example.ItSolutionCore.businesses.attManager.entity.user.employee.Employee;
import com.example.ItSolutionCore.businesses.attManager.entity.user.employee.EmployeeRequestDto;
import com.example.ItSolutionCore.businesses.attManager.entity.user.employee.EmployeeResponseDto;
import com.example.ItSolutionCore.businesses.attManager.repo.CompanyRepository;
import com.example.ItSolutionCore.businesses.attManager.repo.EmployeeRepository;
import com.example.ItSolutionCore.businesses.attManager.repo.UserRepository;
import com.example.ItSolutionCore.common.exception.DataNotFoundException;
import com.example.ItSolutionCore.common.exception.DupeException;
import com.example.ItSolutionCore.common.redis.SimpleKeyValueDtoRepository;
import com.example.ItSolutionCore.common.redis.dto.SimpleKeyValueDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(transactionManager = "attManagerTransactionManager")
public class EmployeeService {

    @PersistenceContext(unitName = "attManager")
    private final EntityManager attManagerEntityManager;
//    private final RedisUtilService redisUtilService;
    private final SimpleKeyValueDtoRepository simpleKeyValueDtoRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;


    public User addEmployee(EmployeeRequestDto employeeRequestDto) throws DataNotFoundException, DupeException {

        // getting the largest EmpID from the cache or DB
          String largestEmpID = simpleKeyValueDtoRepository.findById("latestEmpID").map(SimpleKeyValueDto::getValue).orElse(null);
        log.info("largestEmpID retrieved from Cache:::"+ largestEmpID);
        // data가 cache에 있으면 그대로 cache 값 사용, 없으면 db에서 조회
        if(largestEmpID ==null){
            log.info("largestEmpID cannot be retrieved from Cache.. searching from DB");
           Long MAX_from_DB = employeeRepository.findMostRecentEmployeeId();
           //db에서도 유저 데이터가 아얘 없는 새 table인 경우 200000부터 시작
                 largestEmpID= MAX_from_DB!=null ? String.valueOf(employeeRepository.findMostRecentEmployeeId()) : "200000";
        }
        log.info("largestEmpID:::"+ largestEmpID);

        Optional<Company> companyOpt = companyRepository.findById(employeeRequestDto.getCompany_id());

        if (companyOpt.isPresent()) {
            Company managedCompany = attManagerEntityManager.merge(companyOpt.get());

            // lastName, firstName이 둘 다 같다면 동일인물이므로 트랜젝션 abort
            if(nameDupChecker(userRepository.findAllByCompanyId(managedCompany.getId())
                    .orElseThrow(()->new DataNotFoundException("Cannot find users from the company")), employeeRequestDto)){
                           throw new DupeException("Dupe Firstname and Lastname, user already exists");
            }

            Employee newUser = Employee.builder()
                    .job(employeeRequestDto.getJob())
                    .firstName(employeeRequestDto.getFirstName())
                    .lastName(employeeRequestDto.getLastName())
                    .email(employeeRequestDto.getEmail())
                    .password(employeeRequestDto.getPassword())
                    .company(managedCompany)
                    .profile_pic(employeeRequestDto.getProfile_pic())
                    // employeeID 는 가장 최근 empID에 +1
                    .employeeID(Long.parseLong(largestEmpID)+1)
                    .build();

            //DB::  새로운 User 엔티티를 저장
            attManagerEntityManager.persist(newUser);
            //Cache:: 새로 생성된 Employee의 empID를 attManager:latestEmpID에 저장
            simpleKeyValueDtoRepository.save(new SimpleKeyValueDto("latestEmpID", String.valueOf(Long.parseLong(largestEmpID)+1), 3600L));
            return newUser;
        } else {
            throw new DataNotFoundException("Company not found");
        }

    }

    public List<EmployeeResponseDto> fetchAllEmployeesBy_CompanyId(Long id) throws DataNotFoundException {
       return employeeRepository.findAllEmployeeBy_CompanyId(id).orElseThrow(()-> new DataNotFoundException("Employee not found by CompanyId"))
               .stream().map(Employee::toResponseDto).collect(Collectors.toList());
    }


    private boolean nameDupChecker(List<User> users, UserRequestDto target){
        // lastName, firstName이 동시에 같다면 중복임
       return  users.stream().anyMatch(user-> user.getFirstName().equals(target.getFirstName()) &&
                user.getLastName().equals(target.getLastName()));
    }
}
