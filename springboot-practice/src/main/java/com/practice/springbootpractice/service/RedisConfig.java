package com.practice.springbootpractice.service;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.practice.springbootpractice.models.Converter;



@Configuration
public class RedisConfig {

    // too save variables
    
    // private static final Logger logger = LoggerFactory.getLogger(redisConfig.class);

    // @Value("${spring.redis.host}")
    // private String redisHost;

    // @Value("${spring.redis.port}")
    // private Optional<Integer> redisPort;
    
    // @Value("${spring.redis.password}")
    // private String redisPassword;

    // @Bean
    // @Scope("singleton")
    // public RedisTemplate<String, Object> redisTemplate() {
    //     final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
    //     config.setHostName(redisHost);
    //     config.setPort(redisPort.get());
    //     config.setPassword(redisPassword);

    //     final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
    //     final JedisConnectionFactory jedisFactory = new JedisConnectionFactory(config, jedisClient);
    //     jedisFactory.afterPropertiesSet();

    //     final RedisTemplate<String, Object> template = new RedisTemplate<>();
    //     template.setConnectionFactory(jedisFactory);
    //     template.setKeySerializer(new StringRedisSerializer());
    //     template.setValueSerializer(new StringRedisSerializer());
    //     return template;
    // } 

    // to save object

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Optional<Integer> redisPort;

    private String redisPassword = System.getenv("REDIS_KEY");


    @Bean(name = "games")
    @Scope("singleton")
    public RedisTemplate<String, Converter> redisTemplate() {
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort.get());
        config.setPassword(redisPassword);
        Jackson2JsonRedisSerializer jackson2JsonJsonSerializer = new Jackson2JsonRedisSerializer(Converter.class);

        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();
        RedisTemplate<String, Converter> template = new RedisTemplate<String, Converter>();
        template.setConnectionFactory(jedisFac);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonJsonSerializer);
        template.setHashKeySerializer(template.getKeySerializer());
        template.setHashValueSerializer(template.getValueSerializer());
        return template;
    }
}

