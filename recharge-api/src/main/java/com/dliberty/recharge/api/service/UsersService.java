package com.dliberty.recharge.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dliberty.recharge.api.vo.UsersAddVo;
import com.dliberty.recharge.api.vo.UsersUpdateStatusVo;
import com.dliberty.recharge.dto.UsersDto;
import com.dliberty.recharge.entity.Users;
import com.dliberty.recharge.vo.conditions.UsersQueryVo;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author GuoJingtao
 * @since 2019-03-20
 */
public interface UsersService extends IService<Users> {

	/**
	 * 根据openId查找用户
	 * @param openId
	 * @return
	 */
	Users selectUserByUsername(String username);
	
	/**
	 * 微信用户注册
	 * @param openId
	 * @return
	 */
	Users register(String username,String password);
	
	/**
	 * 分页查询
	 * @param vo
	 * @return
	 */
	IPage<UsersDto> listPage(UsersQueryVo vo);
	
	/**
	 * 修改用户状态
	 * @param vo
	 */
	boolean updateStatus(UsersUpdateStatusVo vo);
	
	/**
	 * 添加用户
	 * @param vo
	 * @return
	 */
	boolean addUser(UsersAddVo vo);
	
}
