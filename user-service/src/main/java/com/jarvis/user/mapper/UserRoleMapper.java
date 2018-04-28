package com.jarvis.user.mapper;

import com.jarvis.user.dto.OrgRoleDto;
import com.jarvis.user.dto.RoleDetailDto;
import com.jarvis.user.query.RoleListQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by ZZF on 2018/3/19.
 */
@Mapper
public interface UserRoleMapper {
    /**
     * 分页查询角色数据
     */
    List<OrgRoleDto> findOrgUser(@Param("roleListQuery")RoleListQuery roleListQuery,@Param("start") Integer start);

    /**
     * count角色数据
     */
    Long countOrgUser(@Param("roleListQuery")RoleListQuery roleListQuery);
    /**
     * 关联机构信息查询角色
     */
    RoleDetailDto findRoleDetail(@Param("roleId") Long roleId);
}
