package com.jarvis.user.mapper;

import com.jarvis.user.Dto.UserOrgRoleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by ZZF on 2018/3/19.
 */
@Mapper
public interface UserRoleAssignmentMapper {
    List<UserOrgRoleDto> findUserOrgRoleList(@Param("userId") Long userId);
    void deleteByUserId(@Param("userId") Long userId);
}
