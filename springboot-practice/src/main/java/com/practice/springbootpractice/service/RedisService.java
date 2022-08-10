package com.practice.springbootpractice.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.practice.springbootpractice.models.Converter;

@Service
public class RedisService implements RedisRepo {
    // data to save into redis
    @Autowired
    RedisTemplate<String, Converter> redisTemplate;

    public void save(Converter convert){
        Random r = new Random();
        StringBuilder strBuilder = new StringBuilder();
        while (strBuilder.length() < 8) {
            strBuilder.append(Integer.toHexString(r.nextInt()));
        }
        redisTemplate.opsForValue().set(strBuilder.toString(), convert);
    
    }

    public Converter load(String load){
        Converter redisConvert = redisTemplate.opsForValue().get(load);
        return redisConvert;

    }
}
