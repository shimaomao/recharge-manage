package com.dliberty.recharge.app.util;

import com.dliberty.recharge.common.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author changzzc
 */
@Component
public class DistributedLockUtil {

    private static DistributedLockUtil distributedLockUtil;

    @Autowired
    private RedisClient redisClient;

    @PostConstruct
    public void init(){
        DistributedLockUtil.distributedLockUtil = this;
        DistributedLockUtil.distributedLockUtil.redisClient = this.redisClient;
    }

    /**
     * lockName可以为共享变量名，也可以为方法名，主要是用于模拟锁信息
     */
    public static boolean tryLock(String lockName , long time ){
        System.out.println(Thread.currentThread() + "开始尝试加锁！");
        return distributedLockUtil.redisClient.setNx(lockName, String.valueOf(System.currentTimeMillis() + 5000) , time);
    }

    public static void  releaseLock(String lockName){
        distributedLockUtil.redisClient.del(lockName);
    }
}
