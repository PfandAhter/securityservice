package com.bakirwebservice.securityservice.rest.service.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenBlackListService {

    private final RedisTemplate<String,String> redisTemplate;

    private static final String BLACKLIST_PREFIX = "token:blacklist:";

    public void blackListToken (String token, long ttlMillis){
        String key = BLACKLIST_PREFIX + token;
        redisTemplate.opsForValue().set(key,"blacklisted");
        redisTemplate.expire(key,ttlMillis,java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    public boolean isTokenBlacklisted(String token){
        String key = BLACKLIST_PREFIX + token;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}