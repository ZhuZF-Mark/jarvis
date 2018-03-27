package com.jarvis.user.dao;

import com.jarvis.user.entity.SystemModule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ZZF on 2018/3/12.
 */
public interface SystemModuleDao  extends JpaRepository<SystemModule, Long> {
    //根据模块号查询模块
    SystemModule findByModuleCode(String moduleCode);
}
