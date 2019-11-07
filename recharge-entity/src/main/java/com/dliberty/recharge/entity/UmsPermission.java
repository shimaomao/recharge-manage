package com.dliberty.recharge.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class UmsPermission implements Serializable {
    private Long id;

    /**
     * 父级权限id
     *
     * @mbggenerated
     */
    private Long pid;

    /**
     * 名称
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 权限值
     *
     * @mbggenerated
     */
    private String value;

    /**
     * 图标
     *
     * @mbggenerated
     */
    private String icon;

    /**
     * 权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）
     *
     * @mbggenerated
     */
    private Integer type;

    /**
     * 前端资源路径
     *
     * @mbggenerated
     */
    private String uri;

    /**
     * 启用状态；0->禁用；1->启用
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 排序
     *
     * @mbggenerated
     */
    private Integer sort;

    private static final long serialVersionUID = 1L;

}