package com.example.ItSolutionCore.common.redis;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

/*
* @Deprecated  Not working well
* */

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash(value = "test") // 5분 , timeToLive = 360
@ToString
public class RedisTestEntity {
    @Id  // jakarta.persistence (x) / .data.annotation.Id (o)
    private String id;

    private String token;

    @TimeToLive
    private Long ttl;


    public RedisTestEntity update(String token, long ttl) {
        this.token = token;
        this.ttl = ttl;
        return this;
    }
}
