package com.dliberty.recharge.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author LG
 * @since 2019-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UsersDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String username;

	private String trueName;

	private String email;

	private Integer status;

	private Date createTime;

	private Date updateTime;

}
