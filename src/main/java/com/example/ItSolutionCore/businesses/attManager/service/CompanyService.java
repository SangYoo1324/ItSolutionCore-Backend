package com.example.ItSolutionCore.businesses.attManager.service;


import com.example.ItSolutionCore.businesses.attManager.entity.company.CompanyRequestDto;
import com.example.ItSolutionCore.businesses.attManager.entity.company.Company;
import com.example.ItSolutionCore.businesses.attManager.repo.CompanyRepository;
import com.example.ItSolutionCore.common.exception.DataNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(transactionManager = "attManagerTransactionManager")
public class CompanyService {
    @PersistenceContext(unitName = "attManager")
    private final EntityManager entityManager;
    private final CompanyRepository companyRepository;

    public CompanyRequestDto createCompany(CompanyRequestDto companyRequestDto){
        log.info(companyRequestDto.toString());
        entityManager.persist(companyRequestDto.toCompany());
        entityManager.flush();
       return companyRequestDto;
    }

    public Company fetchCompany(Long id) throws DataNotFoundException {

        log.info("id:: {}",id);
        return companyRepository.findById(id).orElseThrow(()-> new DataNotFoundException("Data Not Found"));

    }

    @Cacheable(cacheNames = "companies", key = "'attManager'", sync= true, cacheManager = "redisCacheManager")
    public List<Company> fetchAllCompany() {

        return companyRepository.findAll();

    }



}
