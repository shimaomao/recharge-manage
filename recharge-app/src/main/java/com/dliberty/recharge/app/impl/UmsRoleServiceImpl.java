package com.dliberty.recharge.app.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dliberty.recharge.api.service.UmsRoleService;
import com.dliberty.recharge.dao.mapper.UmsPermissionMapper;
import com.dliberty.recharge.dao.mapper.UmsRoleMapper;
import com.dliberty.recharge.dao.mapper.UmsRolePermissionRelationMapper;
import com.dliberty.recharge.entity.UmsPermission;
import com.dliberty.recharge.entity.UmsRole;
import com.dliberty.recharge.entity.UmsRoleExample;
import com.dliberty.recharge.entity.UmsRolePermissionRelation;
import com.dliberty.recharge.entity.UmsRolePermissionRelationExample;

/**
 * 后台角色管理Service实现类
 * Created by macro on 2018/9/30.
 */
@Service
public class UmsRoleServiceImpl implements UmsRoleService {
    @Autowired
    private UmsRoleMapper roleMapper;
    @Autowired
    private UmsRolePermissionRelationMapper rolePermissionRelationMapper;
    @Autowired
    private UmsPermissionMapper umsPermissionMapper;
    @Override
    public int create(UmsRole role) {
        role.setCreateTime(new Date());
        role.setStatus(1);
        role.setAdminCount(0);
        role.setSort(0);
        return roleMapper.insert(role);
    }

    @Override
    public int update(Long id, UmsRole role) {
        role.setId(id);
        return roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public int delete(List<Long> ids) {
        UmsRoleExample example = new UmsRoleExample();
        example.createCriteria().andIdIn(ids);
        return roleMapper.deleteByExample(example);
    }

    @Override
    public List<UmsPermission> getPermissionList(Long roleId) {
        return umsPermissionMapper.getPermissionList(roleId);
    }
    
    @Override
    public List<UmsPermission> getPermissionListByAdminId(Long adminId) {
        return umsPermissionMapper.getPermissionListByAdminId(adminId);
    }

    @Override
    public int updatePermission(Long roleId, List<Long> permissionIds) {
        //先删除原有关系
        UmsRolePermissionRelationExample example=new UmsRolePermissionRelationExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        rolePermissionRelationMapper.deleteByExample(example);
        //批量插入新关系
        List<UmsRolePermissionRelation> relationList = new ArrayList<>();
        for (Long permissionId : permissionIds) {
            UmsRolePermissionRelation relation = new UmsRolePermissionRelation();
            relation.setRoleId(roleId);
            relation.setPermissionId(permissionId);
            relationList.add(relation);
        }
        return rolePermissionRelationMapper.insertList(relationList);
    }

    @Override
    public List<UmsRole> list() {
        return roleMapper.selectByExample(new UmsRoleExample());
    }

	@Override
	public List<String> selectCode(Long adminId) {
		return roleMapper.selectCode(adminId);
	}
}
