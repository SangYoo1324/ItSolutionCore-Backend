package com.example.ItSolutionCore.common.redis;


import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

/*
* @Deprecated  Not working well
* */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@RedisHash
public class RedisTestEntity {
    @Id
    private String id;

    @Indexed
    private String token;

    @TimeToLive
    private Long ttl;


    public RedisTestEntity update(String token, long ttl) {
        this.token = token;
        this.ttl = ttl;
        return this;
    }
}
