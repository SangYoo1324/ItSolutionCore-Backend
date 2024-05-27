package com.example.ItSolutionCore.common.redis;


import com.example.ItSolutionCore.common.dto.GenericResponseDto;
import com.example.ItSolutionCore.common.exception.CacheNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class RedisTestApiController {

    private final RedisUtilService redisUtilService;

    @GetMapping("/redis/test/{key}")
    public ResponseEntity<?> raw_getTest(@PathVariable String key) {

        String result = redisUtilService.getData(key);


        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/redis/test/{key}/{value}")
    public ResponseEntity<?> raw_SetTest(@PathVariable String key, @PathVariable String value) {

        redisUtilService.setData(key, value);


        return ResponseEntity.status(HttpStatus.OK).body(GenericResponseDto.builder().response("set").build());
    }


    @DeleteMapping("/redis/test/{key}")
    public ResponseEntity<?> raw_DeleteTest(@PathVariable String key) {
        redisUtilService.deleteData(key);
        return ResponseEntity.status(HttpStatus.OK).body(GenericResponseDto.builder().response("delete").build());
    }



    // Caching Annotation Test

    @PostMapping("/redis/test/annotation/{id}")
    public ResponseEntity<?> annotation_cache_Set(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(redisUtilService.cacheableTest(id));
    }
}