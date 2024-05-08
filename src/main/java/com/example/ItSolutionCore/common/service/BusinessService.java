package com.example.ItSolutionCore.common.service;


import com.example.ItSolutionCore.common.dto.BusinessDto;
import com.example.ItSolutionCore.common.entity.Business;
import com.example.ItSolutionCore.common.auth.entity.Member;
import com.example.ItSolutionCore.common.exception.DataNotFoundException;
import com.example.ItSolutionCore.common.repo.BusinessRepository;
import com.example.ItSolutionCore.common.repo.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BusinessService {


    private final BusinessRepository businessRepository;
    private final MemberRepository memberRepository;

    private final EntityManager entityManager;

    public BusinessDto createBusiness(BusinessDto businessDto) throws PersistenceException {

        log.info(businessDto.toString());

       return  businessRepository.save(Business.builder().name(businessDto.getName()).planType(businessDto.getPlanType()).build()).toDto();
    }


    public List<Business> getAllBusiness() {
        return businessRepository.findAll();
    }

//    public Business updateBusiness(BusinessDto businessDto){
//
//    }



    //    String jpql = "SELECT m from Member m JOIN FETCH m.business b "
//            + "WHERE b.id = :businessId";
//    Set<Member> memberList = new HashSet<>(
//            entityManager.createQuery(jpql, Member.class)
//                    .setParameter("businessId", id)
//                    .getResultList()
//    );
    public List<?> getMembersByBusiness(Long id) throws DataNotFoundException {

//FetchType.Eager
//        return businessRepository.findById(id).orElseThrow(()->new DataNotFoundException("noooooooooo..."))
//                .getMemberList().stream().map(Member::toDto).collect(Collectors.toList());


//Fetch Join
return   entityManager.createQuery("select b from Business b join fetch b.memberList",  Business.class).getSingleResult().getMemberList()
        .stream().map(Member::toDto).collect(Collectors.toList());

        //Regular Join
//        return   entityManager.createQuery("select b from Business b  join fetch b.memberList",  Business.class).getSingleResult().getMemberList()
//        .stream().map(Member::toDto).collect(Collectors.toList());




    }



}
