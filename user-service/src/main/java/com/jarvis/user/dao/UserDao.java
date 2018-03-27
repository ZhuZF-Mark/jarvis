package com.jarvis.user.dao;

import com.jarvis.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.jdbc.Sql;

/**
 * Created by jian on 18-3-9
 */
public interface UserDao extends JpaRepository<User, Long> {
    /**
     * 根据用户名密码查询用户
     */
    User findByUsernameAndPasswordAndEnabled(String username, String password,Boolean enabled);
    /**
     * 根据用户名和手机号模糊查询
     */
    Page<User> findByUsernameLikeAndMobileLikeAndEnabled(String username, String mobile, Boolean enabled, Pageable page);
    /**
     * 根据用户名模糊查询
     */
    Page<User> findByUsernameLikeAndEnabled(String username,  Boolean enabled, Pageable page);
    /**
     * 根据手机号模糊查询
     */
    Page<User> findByMobileLikeAndEnabled( String mobile, Boolean enabled, Pageable page);
    /**
     * 所有有效用户
     */
    Page<User> findByEnabled( Boolean enabled, Pageable page);
    /**
     * 根据用户名查询
     */
    User findByUsernameAndEnabled(String username,Boolean enabled);
}
