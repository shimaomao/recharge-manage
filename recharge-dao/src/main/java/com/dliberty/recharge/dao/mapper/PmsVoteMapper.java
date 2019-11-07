package com.dliberty.recharge.dao.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dliberty.recharge.common.mybatis.mapper.BaseMapper;
import com.dliberty.recharge.dto.PmsVoteDto;
import com.dliberty.recharge.entity.PmsVote;
import com.dliberty.recharge.vo.conditions.PmsVoteQueryVo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LG
 * @since 2019-11-01
 */
public interface PmsVoteMapper extends BaseMapper<PmsVote> {

	/**
	 * 分页查询
	 * @param page
	 * @param vo
	 * @return
	 */
	IPage<PmsVoteDto> listPage(@Param("page") IPage<?> page,@Param("vo")PmsVoteQueryVo vo);
}
