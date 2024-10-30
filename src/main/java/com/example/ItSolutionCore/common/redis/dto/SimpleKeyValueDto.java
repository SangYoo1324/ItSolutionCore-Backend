package com.example.ItSolutionCore.common.redis.dto;

import com.example.ItSolutionCore.common.redis.RedisTestEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash(value = "simple") // 5분 , timeToLive = 360
@ToString
public class SimpleKeyValueDto {

    @Id private String key;
    private String value;
    @TimeToLive
    private Long ttl;

    public SimpleKeyValueDto update(String key, long ttl) {
        this.key = key;
        this.ttl = ttl;
        return this;
    }
}
