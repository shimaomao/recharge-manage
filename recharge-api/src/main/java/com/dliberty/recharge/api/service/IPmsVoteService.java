package com.dliberty.recharge.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dliberty.recharge.dto.PmsVoteDto;
import com.dliberty.recharge.entity.PmsVote;
import com.dliberty.recharge.vo.PmsVoteParamVo;
import com.dliberty.recharge.vo.conditions.PmsVoteQueryVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LG
 * @since 2019-11-01
 */
public interface IPmsVoteService extends IService<PmsVote> {

	/**
	 * 新增
	 * @param vo
	 * @return
	 */
	boolean create(PmsVoteParamVo vo);
	
	/**
	 * 修改
	 * @param id
	 * @param vo
	 * @return
	 */
	boolean update(Integer id,PmsVoteParamVo vo);
	
	/**
	 * 分页查询
	 * @param vo
	 * @return
	 */
	IPage<PmsVoteDto> listPage(PmsVoteQueryVo vo);
	
	/**
	 * 查询详情
	 * @param id
	 * @return
	 */
	PmsVoteDto query(Integer id);
	
	/**
	 * 删除
	 * @param id
	 * @param voteStatus
	 * @return
	 */
	boolean delete(Integer id);
}
