package com.dliberty.recharge.vo.conditions;

import com.dliberty.recharge.common.vo.BaseVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PmsVoteQueryVo extends BaseVo {

	private static final long serialVersionUID = 1L;
	
	private String keyword;
	
	private Integer voteStatus;
}
