package com.jarvis.user.dao;

import com.jarvis.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Dong yangjian on 2018/3/14.
 */
public interface UserRoleDao extends JpaRepository<UserRole, Long> {

    UserRole findByName(String roleName);
}
