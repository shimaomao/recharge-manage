package com.dliberty.recharge.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class UmsAdminRoleRelation implements Serializable {
	private Long id;

	private Long adminId;

	private Long roleId;

	private static final long serialVersionUID = 1L;

}