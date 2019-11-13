package com.dliberty.recharge.api.vo;

import javax.validation.constraints.NotNull;

import com.dliberty.recharge.common.vo.BaseVo;

import lombok.Getter;
import lombok.Setter;

/**
 * 修改用户状态vo
 * @author LG
 *
 */
@Getter
@Setter
public class UsersUpdateStatusVo extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull(message="id is not null")
	private Integer id;
	
	@NotNull(message="status is not null")
	private Integer status;
}
