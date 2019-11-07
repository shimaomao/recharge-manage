package com.dliberty.recharge.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.dliberty.recharge.api.service.UmsRoleService;
import com.dliberty.recharge.api.service.UsersService;
import com.dliberty.recharge.dto.AdminUserDetails;
import com.dliberty.recharge.entity.UmsPermission;
import com.dliberty.recharge.entity.Users;

@Component("myUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UsersService usersService;
	@Autowired
	private UmsRoleService umsRoleService; 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users users = usersService.selectUserByOpenId(username);
		if (users == null) {
			return null;
		}
		List<UmsPermission> permissionList = umsRoleService.getPermissionListByAdminId(users.getId());
		return new AdminUserDetails(users,permissionList);
		
	}

}
