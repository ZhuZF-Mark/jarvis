package com.jarvis.user.dao;

import com.jarvis.user.entity.OrgUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Dong yangjian on 2018/3/14.
 */
public interface OrgUserDao extends JpaRepository<OrgUser, Long> {
    OrgUser findByOrgRefIdAndUserId(Long orgRefId, Long userId);
    List<OrgUser> findByUserId(Long userId);
}
