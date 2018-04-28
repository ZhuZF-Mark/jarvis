package com.jarvis.user.dao;

import com.jarvis.user.entity.DataAccess;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * Created by ZZF on 2018/3/28.
 */
public interface DataAccessDao extends JpaRepository<DataAccess,Long>{
}
