package com.example.ItSolutionCore.common.redis;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/*
 * @Deprecated  Not working well
 * */

public interface RedisTestRepository extends CrudRepository<RedisTestEntity, String> {
    Optional<RedisTestEntity> findByToken(String token);
}
