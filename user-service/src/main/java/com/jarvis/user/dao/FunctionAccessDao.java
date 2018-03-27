package com.jarvis.user.dao;

import com.jarvis.user.entity.FunctionAccess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 * Created by ZZF on 2018/3/20.
 */
public interface FunctionAccessDao extends JpaRepository<FunctionAccess,Long> {
    //查询某角色下的功能权限
    List<FunctionAccess> findByRoleId(Long roleId);
    //删除某角色下的所有功能权限
    void  deleteByRoleId(Long RoleId);
}
