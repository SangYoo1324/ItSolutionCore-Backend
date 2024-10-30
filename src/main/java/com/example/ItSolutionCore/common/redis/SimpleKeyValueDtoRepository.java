package com.example.ItSolutionCore.common.redis;

import com.example.ItSolutionCore.common.redis.dto.SimpleKeyValueDto;
import org.springframework.data.repository.CrudRepository;

public interface SimpleKeyValueDtoRepository extends CrudRepository<SimpleKeyValueDto, String> {


}
