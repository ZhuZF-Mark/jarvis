package com.jarvis.user.dao;

import com.jarvis.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.comparator.BooleanComparator;

/**
 * Created by Dong yangjian on 2018/3/14.
 */
public interface UserRoleDao extends JpaRepository<UserRole, Long> {

    UserRole findByNameAndGlobalRole(String roleName, Boolean global);
}
