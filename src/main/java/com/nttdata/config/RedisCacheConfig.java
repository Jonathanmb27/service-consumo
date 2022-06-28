package com.nttdata.config;


import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
public class RedisCacheConfig {
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(){
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext
                        .SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }
    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer(){
        return builder -> {
            builder.withCacheConfiguration("accountCache",
                    RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(1)))
                    .withCacheConfiguration("detailCache",
                            RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(1)))
                    .withCacheConfiguration("yanquiAccountCache",
                            RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(1)));
        };
    }
}
