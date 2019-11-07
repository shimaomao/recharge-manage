package com.dliberty.recharge.app.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dliberty.recharge.api.service.ITbSysConfigService;
import com.dliberty.recharge.dao.mapper.TbSysConfigMapper;
import com.dliberty.recharge.entity.TbSysConfig;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LG
 * @since 2019-11-07
 */
@Service
public class TbSysConfigServiceImpl extends ServiceImpl<TbSysConfigMapper, TbSysConfig> implements ITbSysConfigService {

}
