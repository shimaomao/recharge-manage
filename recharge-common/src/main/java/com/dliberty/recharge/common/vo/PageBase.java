package com.dliberty.recharge.common.vo;


import lombok.Data;

@Data
public class PageBase {

	protected long pageNo = Integer.valueOf(1);

	protected long pageSize = Integer.valueOf(10);

	private long totalRecords = Long.valueOf(0);
	
	private long totalPage = Long.valueOf(1);

	public PageBase(long totalRecords, long pageNo, long pageSize,long totalPage) {
		this.totalRecords = totalRecords;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalPage = totalPage;
	}
}
