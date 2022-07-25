package com.bootcamp.productservice.service.redis;

import com.bootcamp.productservice.model.Bank_Account;
import com.bootcamp.productservice.model.Product_Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Service;


@Service
public class RedisService {

    @Autowired
    private ReactiveRedisOperations<String, Product_Type> productTypeOps;

    public void saveProductType(String key, Product_Type product_type){

        ReactiveValueOperations<String,Product_Type> ops = productTypeOps.opsForValue();
        ops.set(key,product_type).subscribe();
    }

}
