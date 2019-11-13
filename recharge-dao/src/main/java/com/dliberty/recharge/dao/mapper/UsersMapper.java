package com.dliberty.recharge.dao.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dliberty.recharge.common.mybatis.mapper.BaseMapper;
import com.dliberty.recharge.dto.UsersDto;
import com.dliberty.recharge.entity.Users;
import com.dliberty.recharge.vo.conditions.UsersQueryVo;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author GuoJingtao
 * @since 2019-03-20
 */
public interface UsersMapper extends BaseMapper<Users> {

	/**
	 * 分页查询
	 * @param page
	 * @param vo
	 * @return
	 */
	IPage<UsersDto> listPage(@Param("page") IPage<?> page,@Param("vo")UsersQueryVo vo);
}
