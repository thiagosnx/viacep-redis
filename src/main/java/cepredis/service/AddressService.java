package cepredis.service;


import cepredis.models.Address;
import cepredis.request.RequestAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AddressService {

    private static final String REDIS_KEY_PREFIX = "endereço:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public ResponseEntity saveAddress(RequestAddress requestAddress, long expire){
        String key = REDIS_KEY_PREFIX + requestAddress.hashCode();
        redisTemplate.opsForValue().set(key, requestAddress);
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        return ResponseEntity.ok().build();
    }
    public Address getAddress(int hashCode){
        String key = REDIS_KEY_PREFIX + hashCode;
        return(Address) redisTemplate.opsForValue().get(key);
    }

}
