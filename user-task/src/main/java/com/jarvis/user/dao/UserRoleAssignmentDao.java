package com.jarvis.user.dao;

import com.jarvis.user.entity.UserRoleAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Dong yangjian on 2018/3/14.
 */
public interface UserRoleAssignmentDao extends JpaRepository<UserRoleAssignment, Long> {
    UserRoleAssignment findByOrgUserIdAndRoleId(Long id, Long roleId);
}
