package com.dliberty.recharge.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dliberty.recharge.entity.UmsRole;
import com.dliberty.recharge.entity.UmsRoleExample;

public interface UmsRoleMapper {
    int countByExample(UmsRoleExample example);

    int deleteByExample(UmsRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsRole record);

    int insertSelective(UmsRole record);

    List<UmsRole> selectByExample(UmsRoleExample example);

    UmsRole selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsRole record, @Param("example") UmsRoleExample example);

    int updateByExample(@Param("record") UmsRole record, @Param("example") UmsRoleExample example);

    int updateByPrimaryKeySelective(UmsRole record);

    int updateByPrimaryKey(UmsRole record);
    
    /**
     * 根据用户查询用户的角色code
     * @param adminId
     * @return
     */
    List<String> selectCode(Long adminId);
}