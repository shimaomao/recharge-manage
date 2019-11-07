package com.dliberty.recharge.app.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dliberty.recharge.api.service.ITbRechargeOrderService;
import com.dliberty.recharge.dao.mapper.TbRechargeOrderMapper;
import com.dliberty.recharge.entity.TbRechargeOrder;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LG
 * @since 2019-11-07
 */
@Service
public class TbRechargeOrderServiceImpl extends ServiceImpl<TbRechargeOrderMapper, TbRechargeOrder> implements ITbRechargeOrderService {

}
