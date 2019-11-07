package com.dliberty.recharge.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dliberty.recharge.common.mybatis.mapper.BaseMapper;
import com.dliberty.recharge.dto.RechargeCardDto;
import com.dliberty.recharge.entity.TbRechargeCard;
import com.dliberty.recharge.vo.conditions.RechargeCardQueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LG
 * @since 2019-11-07
 */
public interface TbRechargeCardMapper extends BaseMapper<TbRechargeCard> {

    /**
     * 批量添加
     * @param list
     * @return
     */
    Integer batchInsert(List<TbRechargeCard> list);

    /**
     * 分页查询
     * @param page
     * @param vo
     * @return
     */
    IPage<RechargeCardDto> listPage(@Param("page") IPage<?> page, @Param("vo") RechargeCardQueryVo vo);

}
