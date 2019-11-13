package com.dliberty.recharge.api.vo;

import javax.validation.constraints.NotBlank;

import com.dliberty.recharge.common.vo.BaseVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersAddVo extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotBlank(message="用户名不能为空")
	private String username;
	
	@NotBlank(message="密码不能为空")
	private String password;
}
