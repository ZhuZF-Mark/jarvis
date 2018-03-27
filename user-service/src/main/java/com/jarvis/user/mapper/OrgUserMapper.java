package com.jarvis.user.mapper;

import com.jarvis.user.Dto.OrgUserDetailDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by ZZF on 2018/3/15.
 */
@Mapper
public interface OrgUserMapper {
    /**
     * 关联查询用户机构关联表和机构集团关联表，返回具体用户的机构信息
     * @param userId 用户id
     * @return dto
     */
   List<OrgUserDetailDto> findOrgUserDetail(long userId);
    /**
     * 删除用户关联的数据
     */
    void  deleteByUserId(@Param("userId") long userId);
}
