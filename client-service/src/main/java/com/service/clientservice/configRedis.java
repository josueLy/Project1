package com.service.clientservice;


import com.service.clientservice.model.Business;
import com.service.clientservice.model.Personnel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class configRedis {

    @Bean
    ReactiveRedisOperations<String, Personnel> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Personnel> serializer = new Jackson2JsonRedisSerializer<>(Personnel.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Personnel> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Personnel> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

    @Bean
    ReactiveRedisOperations<String, Business> redisOperationsB(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Business> serializer = new Jackson2JsonRedisSerializer<>(Business.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Business> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Business> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }



//    @Bean
//    public RedisCacheConfiguration cacheConfiguration() {
//        return RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofMinutes(60))
//                .disableCachingNullValues()
//                .serializeValuesWith(RedisSerializationContext
//                .SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
//    }
//    @Bean
//    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
//        return (builder) -> builder
//                .withCacheConfiguration("itemCache",
//                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)))
//                .withCacheConfiguration("customerCache",
//                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)));
//    }

}
