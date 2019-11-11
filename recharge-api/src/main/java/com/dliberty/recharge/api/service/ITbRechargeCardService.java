package com.dliberty.recharge.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dliberty.recharge.dto.RechargeCardDto;
import com.dliberty.recharge.entity.TbRechargeCard;
import com.dliberty.recharge.vo.BatchCreateCardVo;
import com.dliberty.recharge.vo.conditions.RechargeCardQueryVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LG
 * @since 2019-11-07
 */
public interface ITbRechargeCardService extends IService<TbRechargeCard> {

    /**
     * 批量添加
     *
     * @param cardVo
     * @return
     */
    Boolean batchCreateCard(BatchCreateCardVo cardVo);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean delete(Integer id);

    /**
     * 查询列表
     *
     * @return
     */
    IPage<RechargeCardDto> listPage(RechargeCardQueryVo vo);

}