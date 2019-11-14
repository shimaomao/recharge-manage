package com.dliberty.recharge.app.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import com.dliberty.recharge.dto.ExportRechargeCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dliberty.recharge.api.service.ITbRechargeCardService;
import com.dliberty.recharge.app.util.GeneratorCardInfoUtil;
import com.dliberty.recharge.common.constants.Constants;
import com.dliberty.recharge.common.exception.CommonException;
import com.dliberty.recharge.common.utils.BeanUtil;
import com.dliberty.recharge.common.utils.EntityUtil;
import com.dliberty.recharge.common.utils.Md5;
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

	@Autowired
	private Executor asyncServiceExecutor;

	private static final int default_size = 1000;

	@Override
	public Boolean batchCreateCard(BatchCreateCardVo cardVo) {

		try {
			int number = cardVo.getNumber() / default_size;
			for (int i = 0 ; i <= number ; i++){
				int size = default_size;
				if(i == number){
					size = cardVo.getNumber() - (number * default_size);
				}
				asyncServiceExecutor.execute(new CreatRechargeCardThread(size , cardVo));
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

	@Override
	public RechargeCardDto getRechargeCardInfo(String keyword, Integer type) {
		//（0：id、1:卡号、2:密钥）
		TbRechargeCard card = null;
		RechargeCardDto dto = new RechargeCardDto();
		switch (type){
			case 0:{
				Long id = Long.valueOf(keyword);
				card = baseMapper.selectById(id);
				break;
			}
			case 1:{
				card = baseMapper.selectOne(new QueryWrapper<TbRechargeCard>().eq("card_no", keyword));
				break;
			}
			default:{
				card = baseMapper.selectOne(new QueryWrapper<TbRechargeCard>().eq("secret_key", keyword));
				break;
			}
		}

		if(card != null){
			BeanUtil.copyProperties(card , dto);
			String sign = Md5.MD5(dto.getCardNo() + dto.getSecretKey() + dto.getMoney() + dto.getIsUse());
			dto.setSign(sign);
			return dto;
		}
		return null;
	}

	@Override
	public List<ExportRechargeCardDto> export(RechargeCardQueryVo vo) {
		vo.setStart((vo.getPageNo() - 1) * vo.getPageSize());
		return baseMapper.queryForExport(vo);
	}

	@Override
	public int exportCount(RechargeCardQueryVo vo) {
		return baseMapper.exportCount(vo);
	}

    @Override
    public List<String> selectMoney(RechargeCardQueryVo vo) {
        return baseMapper.selectMoney(vo);
    }

    class CreatRechargeCardThread implements Runnable{
		private int size ;
		private BatchCreateCardVo cardVo;

		public CreatRechargeCardThread(int size, BatchCreateCardVo cardVo) {
			this.size = size;
			this.cardVo = cardVo;
		}

		@Override
		public void run() {
			if(size >= 1){
				List<TbRechargeCard> list = new ArrayList<>();
				for (int i = 1; i <= size; i++) {
					TbRechargeCard card = new TbRechargeCard();
					card.setCardNo(GeneratorCardInfoUtil.getCardNo(cardVo.getMoney()));
					card.setSecretKey(GeneratorCardInfoUtil.getSecretKey(cardVo.getMoney()));
					card.setCreateTime(new Date());
					card.setIsDeleted(Constants.DeletedFlag.DELETED_NO.getCode());
					card.setIsUse(Constants.UseFlag.USED_NO.getCode());
					card.setCreateUserId(cardVo.getUserId());
					card.setMoney(cardVo.getMoney());
					list.add(card);
				}
				if (list != null && list.size() > 0) {
					baseMapper.batchInsert(list);
				}
			}
		}
	}
}


