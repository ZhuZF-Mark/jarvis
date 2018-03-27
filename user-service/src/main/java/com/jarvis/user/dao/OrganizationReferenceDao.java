package com.jarvis.user.dao;

import com.jarvis.user.entity.OrganizationReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

/**
 *
 * Created by ZZF on 2018/3/14.
 */
public interface OrganizationReferenceDao extends JpaRepository<OrganizationReference,Long> {
    /**
     * 根据机构名模糊查询
     * @param orgName 机构名
     * @return 机构列表
     */
    List<OrganizationReference> findByOrgNameLike(String orgName);
}
