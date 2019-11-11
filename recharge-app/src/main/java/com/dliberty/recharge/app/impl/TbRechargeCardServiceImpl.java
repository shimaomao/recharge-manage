package com.dliberty.recharge.app.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dliberty.recharge.api.service.ITbRechargeCardService;
import com.dliberty.recharge.common.constants.Constants;
import com.dliberty.recharge.common.exception.CommonException;
import com.dliberty.recharge.common.utils.EntityUtil;
import com.dliberty.recharge.common.utils.GeneratorCardInfoUtil;
import com.dliberty.recharge.dao.mapper.TbRechargeCardMapper;
import com.dliberty.recharge.dto.RechargeCardDto;
import com.dliberty.recharge.entity.TbRechargeCard;
import com.dliberty.recharge.vo.BatchCreateCardVo;
import com.dliberty.recharge.vo.conditions.RechargeCardQueryVo;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LG
 * @since 2019-11-07
 */
@Service
@Transactional
public class TbRechargeCardServiceImpl extends ServiceImpl<TbRechargeCardMapper, TbRechargeCard>
		implements ITbRechargeCardService {

	@Override
	public Boolean batchCreateCard(BatchCreateCardVo cardVo) {
		List<TbRechargeCard> list = new ArrayList<>();
		try {
			for (int i = 1; i <= cardVo.getNumber(); i++) {
				TbRechargeCard card = new TbRechargeCard();
				card.setCardNo(GeneratorCardInfoUtil.getCardNo(cardVo.getMoney() / 100));
				card.setSecretKey(GeneratorCardInfoUtil.getSecretKey(cardVo.getMoney() / 100));
				card.setCreateTime(new Date());
				card.setIsDeleted(Constants.DeletedFlag.DELETED_NO.getCode());
				card.setIsUse(Constants.UseFlag.USED_NO.getCode());
				card.setCreateUserId(cardVo.getUserId());
				card.setMoney(cardVo.getMoney());
				list.add(card);
			}
			if (list != null && list.size() > 0) {
				saveBatch(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public boolean delete(Integer id) {

		TbRechargeCard card = baseMapper.selectById(id);
		if (card == null) {
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
