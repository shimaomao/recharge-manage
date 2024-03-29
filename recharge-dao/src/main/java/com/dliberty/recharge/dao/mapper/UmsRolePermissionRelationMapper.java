package com.dliberty.recharge.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dliberty.recharge.entity.UmsRolePermissionRelation;
import com.dliberty.recharge.entity.UmsRolePermissionRelationExample;

public interface UmsRolePermissionRelationMapper {
    int countByExample(UmsRolePermissionRelationExample example);

    int deleteByExample(UmsRolePermissionRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsRolePermissionRelation record);

    int insertSelective(UmsRolePermissionRelation record);

    List<UmsRolePermissionRelation> selectByExample(UmsRolePermissionRelationExample example);

    UmsRolePermissionRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsRolePermissionRelation record, @Param("example") UmsRolePermissionRelationExample example);

    int updateByExample(@Param("record") UmsRolePermissionRelation record, @Param("example") UmsRolePermissionRelationExample example);

    int updateByPrimaryKeySelective(UmsRolePermissionRelation record);

    int updateByPrimaryKey(UmsRolePermissionRelation record);
    /**
     * 批量插入角色和权限关系
     */
    int insertList(@Param("list")List<UmsRolePermissionRelation> list);

    
}