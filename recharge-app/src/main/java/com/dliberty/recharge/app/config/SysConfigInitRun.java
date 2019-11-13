package com.dliberty.recharge.app.config;

import com.dliberty.recharge.app.util.ConfigUtil;
import com.dliberty.recharge.common.redis.RedisClient;
import com.dliberty.recharge.entity.TbSysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author changzzc
 */
@Component
public class SysConfigInitRun implements CommandLineRunner {

    @Autowired
    private RedisClient redisClient;

    @Override
    public void run(String... args) throws Exception {

        List<TbSysConfig> configList = ConfigUtil.getAll();
        if(configList != null && configList.size() > 0){
            System.out.println("配置表数据写入到缓存中");
            configList.forEach(config ->{
                redisClient.set(config.getConfigKey() , config.getConfigValue() , 30 * 60);
            });
        }

    }
}
