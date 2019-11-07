package com.dliberty.recharge.common.utils;

import com.dliberty.recharge.common.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GeneratorCardInfoUtil {

    private static GeneratorCardInfoUtil generatorCardInfoUtil;

    @Autowired
    private RedisClient redisClient;


}
