package com.example.ItSolutionCore.businesses.itSolution.api;


import com.example.ItSolutionCore.businesses.itSolution.dto.QnaDto;
import com.example.ItSolutionCore.common.dto.GenericResponseDto;
import com.example.ItSolutionCore.common.exception.DataNotFoundException;
import com.example.ItSolutionCore.businesses.itSolution.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class QnaApiController {

    private final QnaService qnaService;

    @PostMapping("/qna")
    public ResponseEntity<?> postQna(@RequestBody QnaDto qnaDto){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(qnaService.postQna(qnaDto));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    GenericResponseDto.builder().error("member not found").build()
            );
        }
    }

    @GetMapping("/qna")
    public ResponseEntity<?> fetchAllQna(){
        return ResponseEntity.status(HttpStatus.OK).body(qnaService.fetchAll());
    }

    @GetMapping("/qna/{id}")
    public ResponseEntity<?> fetchQna(@PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(qnaService.fetchSingle(id));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    GenericResponseDto.builder().error("qna not found").build());
        }
    }

    @DeleteMapping("/api/qna/{id}")
    public ResponseEntity<?> fetchAllQna(@PathVariable Long id){
        try {
            qnaService.deleteQna(id);
            return ResponseEntity.status(HttpStatus.OK).body(  GenericResponseDto.builder().error("qna successfully deleted").build());
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    GenericResponseDto.builder().error("qna not found").build());
        }
    }
}
