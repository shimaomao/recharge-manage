package com.dliberty.recharge.common.vo;

import java.io.Serializable;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;

@Data
public class BaseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Integer pageNo = Integer.valueOf(1);
	protected Integer pageSize = Integer.valueOf(10);

	private String paramCache;
	private String requestIp;
	private String requestURL;
	private Long userId;
	private String userName;

	public Page<?> getPage() {
		return new Page<>(this.pageNo, this.pageSize);
	}
}
