package com.jarvis.user.mapper;

import com.jarvis.user.entity.OrganizationReference;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * Created by ZZF on 2018/3/22.
 */
@Mapper
public interface OrganizationReferenceMapper {
    /**
     * 根据机构名模糊查询
     * @param orgName 机构名
     * @return 机构列表
     */
    List<OrganizationReference> findByOrgNameLike(@Param("orgName") String orgName);
}
