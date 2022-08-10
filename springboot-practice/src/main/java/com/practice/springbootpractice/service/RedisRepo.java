package com.practice.springbootpractice.service;

import com.practice.springbootpractice.models.Converter;

public interface RedisRepo {
    public void save(Converter convert);
}
