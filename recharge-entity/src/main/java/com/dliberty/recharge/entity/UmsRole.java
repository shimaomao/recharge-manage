package com.dliberty.recharge.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class UmsRole implements Serializable {
	private Long id;

	/**
	 * 名称
	 *
	 * @mbggenerated
	 */
	private String name;

	/**
	 * 描述
	 *
	 * @mbggenerated
	 */
	private String description;

	/**
	 * 角色code
	 */
	private String code;

	/**
	 * 后台用户数量
	 *
	 * @mbggenerated
	 */
	private Integer adminCount;

	/**
	 * 创建时间
	 *
	 * @mbggenerated
	 */
	private Date createTime;

	/**
	 * 启用状态：0->禁用；1->启用
	 *
	 * @mbggenerated
	 */
	private Integer status;

	private Integer sort;

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", id=").append(id);
		sb.append(", name=").append(name);
		sb.append(", description=").append(description);
		sb.append(", adminCount=").append(adminCount);
		sb.append(", createTime=").append(createTime);
		sb.append(", status=").append(status);
		sb.append(", sort=").append(sort);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}