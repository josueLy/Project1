package com.service.clientservice.service.redis;

import com.service.clientservice.model.Personnel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class RedisService {

    @Autowired
    private ReactiveRedisOperations<String, Personnel> personnelOps;

    public void savePersonnel(String key, Personnel personnel)
    {

        ReactiveValueOperations<String,Personnel> ops =personnelOps.opsForValue();
        ops.set(key,personnel).subscribe();

//        Mono<String> personnelString=  ops.get
//
//        personnelString.subscribe(System.out::println);
    }


}
