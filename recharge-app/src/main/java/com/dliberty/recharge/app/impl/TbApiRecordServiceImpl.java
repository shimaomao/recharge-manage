package com.dliberty.recharge.app.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dliberty.recharge.api.service.ITbApiRecordService;
import com.dliberty.recharge.dao.mapper.TbApiRecordMapper;
import com.dliberty.recharge.entity.TbApiRecord;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LG
 * @since 2019-11-07
 */
@Service
@Transactional
public class TbApiRecordServiceImpl extends ServiceImpl<TbApiRecordMapper, TbApiRecord> implements ITbApiRecordService {

}
