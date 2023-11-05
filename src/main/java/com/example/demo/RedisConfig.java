package com.example.demo;

import java.time.Duration;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.Jedis;

@Configuration
public class RedisConfig {

@Bean
JedisConnectionFactory jedisConnectionFactory() {
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
    redisStandaloneConfiguration.setHostName("localhost");
    redisStandaloneConfiguration.setPort(6379);
    redisStandaloneConfiguration.setDatabase(0);
    
    GenericObjectPoolConfig<Jedis> genericObjectPoolConfig = new GenericObjectPoolConfig<Jedis>();
	genericObjectPoolConfig.setMaxTotal(10); 
	genericObjectPoolConfig.setMaxIdle(5); 
	genericObjectPoolConfig.setMinIdle(2);

    JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
    jedisClientConfiguration.connectTimeout(Duration.ofSeconds(1));// 1s connection timeout
    jedisClientConfiguration.usePooling().poolConfig(genericObjectPoolConfig);
    
    return new JedisConnectionFactory(redisStandaloneConfiguration,
            jedisClientConfiguration.build());
}

@Bean
public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(jedisConnectionFactory());
    return template;
}
}