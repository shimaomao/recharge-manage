package com.dliberty.recharge.app.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dliberty.recharge.dao.mapper.TbSysConfigMapper;
import com.dliberty.recharge.entity.TbSysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author zhangzhichang
 * @date 2019-11-12 11:01
 * @description
 */
@Component
public class ConfigUtil {

    @Autowired
    private TbSysConfigMapper configMapper;

    public static ConfigUtil configUtil;

    @PostConstruct
    public void init(){
        ConfigUtil.configUtil = this;
        ConfigUtil.configUtil.configMapper = this.configMapper;
    }

    /**
     * @param key
     * @return
     */
    public static String getString(String key){
        try{
            TbSysConfig config = configUtil.configMapper.selectOne(new QueryWrapper<TbSysConfig>().eq("configKey", key));
            if(config!=null){
                return config.getConfigValue();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(String key , String defaultValue){
        try{
            TbSysConfig config = configUtil.configMapper.selectOne(new QueryWrapper<TbSysConfig>().eq("configKey", key));
            if(config!=null){
                return config.getConfigValue();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return defaultValue;
    }

    /**
     * 查询所有
     * @return
     */
    public static List<TbSysConfig> getAll(){
        return configUtil.configMapper.selectList(new QueryWrapper<>());
    }
}
