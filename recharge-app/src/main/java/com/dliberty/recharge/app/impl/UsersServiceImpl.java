package com.dliberty.recharge.app.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dliberty.recharge.api.service.UsersService;
import com.dliberty.recharge.api.vo.UsersAddVo;
import com.dliberty.recharge.api.vo.UsersUpdateStatusVo;
import com.dliberty.recharge.dao.mapper.UsersMapper;
import com.dliberty.recharge.dto.UsersDto;
import com.dliberty.recharge.entity.Users;
import com.dliberty.recharge.vo.conditions.UsersQueryVo;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author GuoJingtao
 * @since 2019-03-20
 */
@Service
@Transactional
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Users selectUserByUsername(String username) {
		QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", username);
		queryWrapper.orderByDesc("id");
		List<Users> selectList = baseMapper.selectList(queryWrapper);
		if (selectList.size() > 0) {
			return selectList.get(0);
		}
		return null;
	}

	@Override
	public Users register(String username,String password) {
		try {
			Users users = selectUserByUsername(username);
			if (users != null) {
				return users;
			}
			users = new Users();
			users.setUsername(username);
			users.setCreateTime(new Date());
			users.setUpdateTime(new Date());
			users.setStatus(1);
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String encode = encoder.encode(password);
			users.setPassword(encode);
			save(users);
			return users;
		} catch (Exception e) {
			logger.info("保存用户信息发生异常{}",e.getMessage());
			return null;
		}
		
		
	}

	@Override
	public IPage<UsersDto> listPage(UsersQueryVo vo) {
		vo.getPage().setOptimizeCountSql(false);
		vo.getPage().setSearchCount(false);
		return baseMapper.listPage(vo.getPage(), vo);
	}

	@Override
	public boolean updateStatus(UsersUpdateStatusVo vo) {
		Users user = baseMapper.selectById(vo.getId());
		if (user != null) {
			user.setStatus(vo.getStatus());
		}
		return updateById(user);
	}

	@Override
	public boolean addUser(UsersAddVo vo) {
		register(vo.getUsername(), vo.getPassword());
		return true;
	}


}
