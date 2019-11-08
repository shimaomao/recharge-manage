package com.dliberty.recharge.app.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dliberty.recharge.common.constants.Constants;
import com.dliberty.recharge.common.exception.CommonException;
import com.dliberty.recharge.common.redis.RedisClient;
import com.dliberty.recharge.common.utils.EntityUtil;
import com.dliberty.recharge.dto.RechargeCardDto;
import com.dliberty.recharge.vo.CreateCardVo;
import com.dliberty.recharge.vo.conditions.RechargeCardQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dliberty.recharge.api.service.ITbRechargeCardService;
import com.dliberty.recharge.dao.mapper.TbRechargeCardMapper;
import com.dliberty.recharge.entity.TbRechargeCard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    public Boolean batchCreateCard(CreateCardVo cardVo) {
        List<TbRechargeCard> list = new ArrayList<>();
        try {
            cardVo.getCardList().forEach(create -> {
                for(int i = 1 ; i <= create.getNumber() ; i++){
                    TbRechargeCard card = new TbRechargeCard();
                    card.setCardNo(GeneratorCardInfoUtil.getCardNo(card.getMoney()/100));
                    card.setSecretKey(GeneratorCardInfoUtil.getCardNo(card.getMoney()/100));
                    card.setCreateTime(new Date());
                    card.setIsDeleted(Constants.DeletedFlag.DELETED_NO.getCode());
                    card.setIsUse(Constants.UseFlag.USED_NO.getCode());
                    card.setCreateUserId(cardVo.getUserId());
                    card.setMoney(create.getMoney()/100);
                    list.add(card);
                    if(list.size()>=1000){
                        baseMapper.batchInsert(list);
                        list.clear();
                    }
                }
            });
            if(list != null && list.size() > 0){
                baseMapper.batchInsert(list);
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(Integer id) {

        TbRechargeCard card = baseMapper.selectById(id);
        if(card == null){
            throw new CommonException("删除失败");
        }
        card.setIsDeleted(Constants.DeletedFlag.DELETED_YES.getCode());
        EntityUtil.setUpdateValue(card);
        return updateById(card);
    }

    @Override
    public IPage<RechargeCardDto> listPage(RechargeCardQueryVo vo) {
        vo.getPage().setOptimizeCountSql(false);
        vo.getPage().setSearchCount(false);
        return baseMapper.listPage(vo.getPage(), vo);
    }
}
