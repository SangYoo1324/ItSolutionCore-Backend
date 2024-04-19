package com.example.ItSolutionCore.businesses.itSolution.entity;


import com.example.ItSolutionCore.businesses.itSolution.dto.QnaDto;
import com.example.ItSolutionCore.common.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Qna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="member_id", referencedColumnName = "id")
    private Member member;

    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    private Timestamp date;


    public QnaDto toDto(){
        return QnaDto.builder()
                .id(this.getId())
                .content(this.getContent())
                .member_id(this.getMember().getId())
                .title(this.getTitle())
                .date(this.date.toString().substring(0,10))
                .build();
    }
}
