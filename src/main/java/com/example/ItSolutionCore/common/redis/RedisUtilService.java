package com.example.ItSolutionCore.common.redis;

import com.example.ItSolutionCore.common.dto.GenericResponseDto;
import com.example.ItSolutionCore.common.exception.CacheNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RedisUtilService {

   private final RedisTemplate<String, Object> redisTemplate;

   private final RedisTestRepository redisTestRepository;

    private final CacheManager cacheManager;

    public String getData(String key, String cacheNames){

        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key+"::"+cacheNames) != null ? String.valueOf(valueOperations.get(key+":"+cacheNames) ):  null;
    }


    public void setData(String key,String value, String cacheNames) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key+"::"+cacheNames, value);
    }

    public void setDataExpire(String key, String value,String cacheNames ,long duration){
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        Duration expDura = Duration.ofSeconds(duration);
        valueOperations.set(key+"::"+cacheNames,value,expDura);
    }


    public void deleteData(String key, String cacheNames) {
        redisTemplate.delete(key+":"+cacheNames);
    }


    //for GET request : if return value data exists, just return the thing or save and return
    //Before the method executed, cache research will be executed with key value.
    // if cache exist with the same key, just return the cache without executing method
    @Cacheable(cacheNames = "test", key = "#id", sync = true, cacheManager = "redisCacheManager")
    public RedisTestEntity cacheableTest(Long id){
        return RedisTestEntity.builder()
                .id("1")
                .token("token")
                .build();
    }

// for UPDATE request : it always execute the method
    @CachePut(cacheNames = "test", key= "#id", cacheManager = "redisCacheManager", condition ="#id<10 and #id!=0")
    public RedisTestEntity cachePutTest(Long id){
        return RedisTestEntity.builder()
                .id("1")
                .token("token")
                .build();
    }


    /*
     * @Deprecated  Not working well
     * */
    public RedisTestEntity saveEntity(String id, String token, long ttl){
        RedisTestEntity entity = RedisTestEntity.builder()
                .id(id)
                .token(token)
                .ttl(ttl)
                .build();
    return redisTestRepository.save(entity);
    }




}
