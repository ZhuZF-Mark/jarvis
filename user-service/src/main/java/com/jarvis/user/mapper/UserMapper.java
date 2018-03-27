package com.jarvis.user.mapper;

import com.jarvis.user.entity.User;
import com.jarvis.user.query.UserListQuery;
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
    List<User> findByParams(@Param("userListQuery") UserListQuery userListQuery,@Param("start") Integer start);

    //统计条数
    Long countByParams(@Param("userListQuery") UserListQuery userListQuery);
}