package com.bootcamp.productservice;


import com.bootcamp.productservice.model.Bank_Account;
import com.bootcamp.productservice.model.Product_Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class configRediss {

    @Bean
    ReactiveRedisOperations<String, Product_Type> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Product_Type> serializer = new Jackson2JsonRedisSerializer<>(Product_Type.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Product_Type> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Product_Type> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

    /*
    @Bean
    ReactiveRedisOperations<String, Bank_Account> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Bank_Account> serializer = new Jackson2JsonRedisSerializer<>(Bank_Account.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Product_Type> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Bank_Account> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

     */

}
