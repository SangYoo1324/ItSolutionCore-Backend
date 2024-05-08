package com.example.ItSolutionCore.businesses.itSolution.service;


import com.example.ItSolutionCore.businesses.itSolution.dto.QnaDto;
import com.example.ItSolutionCore.businesses.itSolution.entity.Qna;
import com.example.ItSolutionCore.businesses.itSolution.repo.QnaRepository;
import com.example.ItSolutionCore.common.auth.entity.Member;
import com.example.ItSolutionCore.common.exception.DataNotFoundException;
import com.example.ItSolutionCore.common.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
@Slf4j
public class QnaService {

    private final QnaRepository qnaRepository;
    private final MemberRepository memberRepository;





    public QnaDto postQna(QnaDto qnaDto) throws DataNotFoundException {



        return qnaRepository.save(Qna.builder()
                .title(qnaDto.getTitle())
                .content(qnaDto.getContent())
                .date(Timestamp.valueOf(java.time.LocalDateTime.now()))
                        .member_id(qnaDto.getMember_id())
                .build()).toDto();
    }

    public List<QnaDto> fetchAll(){

//       List<Qna> qnas = entityManager.createQuery("SELECT q from Qna q JOIN FETCH Member m ON q.member.id = m.id", Qna.class).getResultList();
//
        List<Qna> qnas = qnaRepository.findAll();
        log.info("qnas:::"+ qnas);
        return qnas.stream().map(Qna::toDto).collect(Collectors.toList());
    }

    public QnaDto fetchSingle(Long id) throws DataNotFoundException {
        QnaDto target = qnaRepository.findById(id).orElseThrow(()-> new DataNotFoundException("Qna Not found")).toDto();
        return target;
    }

    public void deleteQna(Long id) throws DataNotFoundException {
        Qna target = qnaRepository.findById(id).orElseThrow(()-> new DataNotFoundException("data not found"));
        qnaRepository.delete(target);
    }

}
