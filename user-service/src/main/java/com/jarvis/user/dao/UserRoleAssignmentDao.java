package com.jarvis.user.dao;

import com.jarvis.user.entity.UserRoleAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by ZZF on 2018/3/19.
 */
public interface UserRoleAssignmentDao extends JpaRepository<UserRoleAssignment, Long> {
    void deleteByUserId(Long userId);
}
