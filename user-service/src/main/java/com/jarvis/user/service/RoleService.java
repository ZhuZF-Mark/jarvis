package com.jarvis.user.service;

import com.jarvis.user.dao.UserRoleDao;
import com.jarvis.user.entity.UserRole;
import com.jarvis.user.requestform.RoleFunctionForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Created by ZZF on 2018/3/26.
 */
@Service
public class RoleService {
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private SystemModuleService systemModuleService;

    /**
     * 创建角色
     * @param form 角色表单
     */
    @Transactional
    public void createRole(RoleFunctionForm form){
        UserRole role = new UserRole();
        BeanUtils.copyProperties(form.getRole(), role);
        //插入角色
        role=userRoleDao.save(role);
        form.setRole(role);
        //插入功能清单
        systemModuleService.createRoleFunction(form);
    }
}
