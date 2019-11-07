package com.dliberty.recharge.app.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dliberty.recharge.api.service.ITbRechargeCardService;
import com.dliberty.recharge.dao.mapper.TbRechargeCardMapper;
import com.dliberty.recharge.entity.TbRechargeCard;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LG
 * @since 2019-11-07
 */
@Service
public class TbRechargeCardServiceImpl extends ServiceImpl<TbRechargeCardMapper, TbRechargeCard> implements ITbRechargeCardService {

}
