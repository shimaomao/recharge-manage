package com.dliberty.recharge.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dliberty.recharge.common.mybatis.mapper.BaseMapper;
import com.dliberty.recharge.dto.RechargeOrderDto;
import com.dliberty.recharge.entity.TbRechargeOrder;
import com.dliberty.recharge.vo.conditions.RechargeOrderQueryVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LG
 * @since 2019-11-07
 */
public interface TbRechargeOrderMapper extends BaseMapper<TbRechargeOrder> {

    /**
     * 分页查询
     * @param page
     * @param vo
     * @return
     */
    IPage<RechargeOrderDto> listPage(@Param("page") IPage<?> page, @Param("vo") RechargeOrderQueryVo vo);
}
