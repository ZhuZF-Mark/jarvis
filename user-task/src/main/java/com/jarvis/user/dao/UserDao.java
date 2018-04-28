package com.jarvis.user.dao;

import com.jarvis.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Dong yangjian on 2018/3/13.
 */
public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String userId);

    User findByMobile(String mobile);


}
