package com.dliberty.recharge.api.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dliberty.recharge.entity.UmsPermission;
import com.dliberty.recharge.entity.UmsRole;

/**
 * 后台角色管理Service
 * Created by macro on 2018/9/30.
 */
public interface UmsRoleService {
    /**
     * 添加角色
     */
    int create(UmsRole role);

    /**
     * 修改角色信息
     */
    int update(Long id, UmsRole role);

    /**
     * 批量删除角色
     */
    int delete(List<Long> ids);

    /**
     * 获取指定角色权限
     */
    List<UmsPermission> getPermissionList(Long roleId);
    
    /**
     * 获取指定角色权限
     */
    List<UmsPermission> getPermissionListByAdminId(Long adminId);

    /**
     * 修改指定角色的权限
     */
    @Transactional
    int updatePermission(Long roleId, List<Long> permissionIds);

    /**
     * 获取角色列表
     */
    List<UmsRole> list();
    
    /**
     * 根据用户查询用户的角色code
     * @param adminId
     * @return
     */
    List<String> selectCode(Long adminId);
}
