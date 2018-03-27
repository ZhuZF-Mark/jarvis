package com.jarvis.user.dao;

import com.jarvis.user.entity.OrgUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by ZZF on 2018/3/14.
 */
public interface OrgUserDao extends JpaRepository<OrgUser,Long>{
    List<OrgUser> findByUserIdAndEnabled(long userId,Boolean enabledd);
    /**
     * 根据用户id删除
     */
    void deleteByUserId(Long userId);
}
