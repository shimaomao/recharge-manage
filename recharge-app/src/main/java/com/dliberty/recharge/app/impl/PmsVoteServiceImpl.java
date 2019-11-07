package com.dliberty.recharge.app.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dliberty.recharge.api.service.IPmsVoteService;
import com.dliberty.recharge.common.constants.Constants;
import com.dliberty.recharge.common.exception.CommonException;
import com.dliberty.recharge.common.lang.data.IntUtils;
import com.dliberty.recharge.common.utils.BeanUtil;
import com.dliberty.recharge.common.utils.EntityUtil;
import com.dliberty.recharge.dao.mapper.PmsVoteMapper;
import com.dliberty.recharge.dto.PmsVoteDto;
import com.dliberty.recharge.entity.PmsVote;
import com.dliberty.recharge.vo.PmsVoteParamVo;
import com.dliberty.recharge.vo.conditions.PmsVoteQueryVo;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LG
 * @since 2019-11-01
 */
@Service
@Transactional
public class PmsVoteServiceImpl extends ServiceImpl<PmsVoteMapper, PmsVote> implements IPmsVoteService {

	@Override
	public boolean create(PmsVoteParamVo vo) {
		if (IntUtils.equalInt(vo.getIsOpenSignup(), Constants.COMMON_FLAG_YES)) {
			if (vo.getSignupBeginTime() == null || vo.getSignupEndTime() == null) {
				throw new CommonException("报名时间不能为空");
			}
		}
		PmsVote vote = new PmsVote();
		BeanUtil.copyProperties(vo, vote);
		EntityUtil.setValue(vote);
		return save(vote);
	}

	@Override
	public boolean update(Integer id, PmsVoteParamVo vo) {
		if (IntUtils.equalInt(vo.getIsOpenSignup(), Constants.COMMON_FLAG_YES)) {
			if (vo.getSignupBeginTime() == null || vo.getSignupEndTime() == null) {
				throw new CommonException("报名时间不能为空");
			}
		}
		PmsVote vote = baseMapper.selectById(id);
		Optional.ofNullable(vote).ifPresent(v -> {
			BeanUtil.copyProperties(vo, v);
			EntityUtil.setUpdateValue(v);
		});
		return updateById(vote);
	}

	@Override
	public IPage<PmsVoteDto> listPage(PmsVoteQueryVo vo) {
		vo.getPage().setOptimizeCountSql(false);
		vo.getPage().setSearchCount(false);
		return baseMapper.listPage(vo.getPage(), vo);
	}

	@Override
	public PmsVoteDto query(Integer id) {
		PmsVoteDto dto = new PmsVoteDto();
		Optional.ofNullable(baseMapper.selectById(id)).ifPresent(v -> {
			BeanUtil.copyProperties(v, dto);
		});
		return dto;
	}

	@Override
	public boolean delete(Integer id) {
		PmsVote vote = baseMapper.selectById(id);
		if (vote == null) {
			throw new CommonException("删除失败");
		}
		vote.setIsDeleted(Constants.COMMON_FLAG_YES);
		EntityUtil.setUpdateValue(vote);

		return updateById(vote);
	}

}
