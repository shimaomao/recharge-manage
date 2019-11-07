package com.dliberty.recharge.vo;

import com.dliberty.recharge.common.vo.BaseVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PmsVoteVerifyVo extends BaseVo {

	private static final long serialVersionUID = 1L;
	
	private Integer id;

	/**
     * 状态（10：已提交，20：审核通过，30：审核驳回）
     */
    private Integer voteStatus;

    /**
     * 驳回意见
     */
    private String rejectReason;

}
