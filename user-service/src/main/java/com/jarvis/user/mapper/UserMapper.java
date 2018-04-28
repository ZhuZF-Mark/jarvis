package com.jarvis.user.mapper;

import com.jarvis.user.dto.OrgUserDto;
import com.jarvis.user.dto.UserListDto;
import com.jarvis.user.entity.User;
import com.jarvis.user.query.UserListQuery;
import com.jarvis.user.requestform.WxBindForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * Created by ZZF on 2018/3/15.
 */
@Mapper
public interface UserMapper {
    //多条件查询用户信息
    List<UserListDto> findByParams(@Param("userListQuery") UserListQuery userListQuery, @Param("start") Integer start);

    //统计条数
    Long countByParams(@Param("userListQuery") UserListQuery userListQuery);

    User queryById(Long userId);
    //根据微信绑定id查询
    List<User> findByUnionId(@Param("unionId") String unionId);
    //绑定用户微信
    void updateUserWxUnionId(WxBindForm wxBindForm);
    //根据手机号密码机构id查询用户信息
    OrgUserDto selectOrgUserByMobile(@Param("mobile") String mobile,@Param("password") String password,@Param("agencyId") Long agencyId);
    //根据微信id机构id查询用户信息
    OrgUserDto selectOrgUserByUnionId(@Param("unionId") String unionId,@Param("agencyId") Long agencyId);
}