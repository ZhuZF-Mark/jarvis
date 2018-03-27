package com.jarvis.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * Created by Dong yangjian on 2018/3/15.
 */
@Mapper
public interface UserTaskTimeMapper {

    Date findLastUpdateTime();

    void updateLastUpdateTime(@Param("lastUpdateTime") Date lastUpdateTime);
}
