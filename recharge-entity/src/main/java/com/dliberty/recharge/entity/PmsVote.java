package com.dliberty.recharge.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

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
public class PmsVote implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 投票主题名称
     */
    private String voteTitle;

    /**
     * 封面
     */
    private String voteBanner;

    /**
     * 投票介绍
     */
    private String voteIntroduce;

    /**
     * 报名开始时间
     */
    private Date signupBeginTime;

    /**
     * 报名结束时间
     */
    private Date signupEndTime;
    
    /**
     * 投票开始时间
     */
    private Date voteBeginTime;

    /**
     * 投票结束时间
     */
    private Date voteEndTime;

    /**
     * 是否开放报名
     */
    private Integer isOpenSignup;

    /**
     * 每日投票次数
     */
    private Integer everydayVoteNum;

    /**
     * 是否重复投票
     */
    private Integer isRepeatVote;

    /**
     * 是否展示
     */
    private Integer isShow;

    /**
     * 分享图片
     */
    private String shareImg;

    /**
     * 状态（10：已提交，20：审核通过，30：审核驳回）
     */
    private Integer voteStatus;

    /**
     * 驳回意见
     */
    private String rejectReason;
    
    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 主办方
     */
    private String sponsor;

    /**
     * 主办方电话
     */
    private String sponsorPhone;
    
    /**
	 * 浏览量
	 */
	private Integer browse;

    private Date createTime;

    private Date updateTime;

    /**
     * 删除状态
     */
    private Integer isDeleted;


}
