package com.example.ItSolutionCore.common.repo;

import com.example.ItSolutionCore.common.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT m from Member m where m.business.id = :businessId")
    List<Member> findBusinessWithMembers(@Param("businessId") Long businessId);

    Optional<Member> findByUsernameAndEmail(String username, String email);

    Optional<Member> findByUsername(String username);
}
