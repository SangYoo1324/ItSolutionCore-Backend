package com.example.ItSolutionCore.businesses.itSolution.entity;


import com.example.ItSolutionCore.businesses.itSolution.dto.QnaDto;
import com.example.ItSolutionCore.common.auth.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="qna")
public class Qna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

/*     No Choice. Each Basic & Advanced plan businesses will have their own DB.
     But, Member DB is universal use, so It's in the common DB.*/
    private Long member_id;

    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    private Timestamp date;


    public QnaDto toDto(){
        return QnaDto.builder()
                .id(this.getId())
                .content(this.getContent())
                .member_id(member_id)
                .title(this.getTitle())
                .date(this.date.toString().substring(0,10))
                .build();
    }
}
