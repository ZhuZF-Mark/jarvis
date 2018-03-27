package com.jarvis.user.dao;

import com.jarvis.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by ZZF on 2018/3/15.
 */
public interface UserRoleDao extends JpaRepository<UserRole, Long> {
    List<UserRole> findByOrgRefIdOrGlobalRole(Long orgRefId,Boolean globalRole);
}
