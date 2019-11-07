package com.dliberty.recharge.vo;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.dliberty.recharge.common.vo.BaseVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PmsVoteParamVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	/**
	 * 投票主题名称
	 */
	@NotBlank(message = "主题名称不能为空")
	@Size(max = 50, message = "主题名称不能超过50个字符")
	@ApiModelProperty("投票主题")
	private String voteTitle;

	/**
	 * 封面
	 */
	@NotBlank(message = "封面不能为空")
	@ApiModelProperty("封面")
	private String voteBanner;

	/**
	 * 投票介绍
	 */
	@NotBlank(message = "介绍不能为空")
	@ApiModelProperty("投票介绍")
	private String voteIntroduce;

	/**
	 * 报名开始时间
	 */
	@ApiModelProperty("报名开始时间")
	private Date signupBeginTime;

	/**
	 * 报名结束时间
	 */
	@ApiModelProperty("报名结束时间")
	private Date signupEndTime;
	
	/**
	 * 投票开始时间
	 */
	@ApiModelProperty("投票开始时间")
	private Date voteBeginTime;

	/**
	 * 投票结束时间
	 */
	@ApiModelProperty("投票结束时间")
	private Date voteEndTime;

	/**
	 * 是否开放报名
	 */
	@Max(value = 2, message = "接口错误")
	@ApiModelProperty("是否开放报名")
	private Integer isOpenSignup;

	/**
	 * 每日投票次数
	 */
	@NotNull(message = "每日投票次数不能为空")
	@ApiModelProperty("每日投票次数")
	private Integer everydayVoteNum;

	/**
	 * 是否重复投票
	 */
	@Max(value = 2, message = "接口错误")
	@ApiModelProperty("是否重复投票")
	private Integer isRepeatVote;

	/**
	 * 是否展示
	 */
	@Max(value = 2, message = "接口错误")
	@ApiModelProperty("是否展示")
	private Integer isShow;

	/**
	 * 分享图片
	 */
	@ApiModelProperty("分享图片")
	private String shareImg;

	/**
	 * 主办方
	 */
	@Size(max = 50, message = "接口错误")
	@ApiModelProperty("主办方")
	private String sponsor;

	/**
	 * 主办方电话
	 */
	@Size(max = 50, message = "接口错误")
	@ApiModelProperty("主办方电话")
	private String sponsorPhone;
	
	/**
	 * 浏览量
	 */
	@ApiModelProperty("浏览量")
	private Integer browse;
	
	/**
	 * 状态
	 */
	@ApiModelProperty("状态")
	private Integer voteStatus;

}
