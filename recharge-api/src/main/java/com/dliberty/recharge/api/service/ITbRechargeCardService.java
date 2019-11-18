package com.dliberty.recharge.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dliberty.recharge.dto.ExportRechargeCardDto;
import com.dliberty.recharge.dto.RechargeCardDto;
import com.dliberty.recharge.entity.TbRechargeCard;
import com.dliberty.recharge.vo.BatchCreateCardVo;
import com.dliberty.recharge.vo.conditions.RechargeCardQueryVo;

import java.util.List;

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

    /**
     * 查询单个充值卡
     * @param keyword 查询关键字
     * @param type 查询类型（0：id、1:卡号、2:密钥）
     * @return
     */
    RechargeCardDto getRechargeCardInfo(String keyword , Integer type);

    /**
     * 导出
     *
     * @return
     */
    List<ExportRechargeCardDto> export(RechargeCardQueryVo vo);

    /**
     * 统计
     * @param vo
     * @return
     */
    int exportCount(RechargeCardQueryVo vo);
}