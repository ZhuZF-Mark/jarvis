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

    /**
     * 根据用户手机号和密码查询机构列表
     * @param mobile
     * @return
     */
    List<OrganizationReference> findByUserMobile(@Param("mobile") String mobile,@Param("password") String password);
    /**
     * 根据用户微信号查询机构列表
     * @param unionId
     * @return
     */
    List<OrganizationReference> findByWxUnionId(@Param("unionId") String unionId);
}
