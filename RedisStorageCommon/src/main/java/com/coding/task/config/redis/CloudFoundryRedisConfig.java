package com.coding.task.config.redis;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.PooledServiceConnectorConfig;
import org.springframework.cloud.service.PooledServiceConnectorConfig.PoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@Profile("cloud")
@EnableRedisRepositories
@EnableAutoConfiguration(exclude={RedisReactiveAutoConfiguration.class})
public class CloudFoundryRedisConfig extends AbstractCloudConfig {
	
	public CloudFoundryRedisConfig() {
		super();
	}
	
	@Bean
	public RedisConnectionFactory redisFactory() {
		PoolConfig poolConfig = new PoolConfig(5, 30, 3000);//start with 5 connections, max 30, wait 3seconds
		PooledServiceConnectorConfig redisConfig = new PooledServiceConnectorConfig(poolConfig);
	    return connectionFactory().redisConnectionFactory(redisConfig);
	}

}
