package com.jarvis.user.service;

import com.jarvis.user.dto.FunctionPointDto;
import com.jarvis.user.dao.FunctionAccessDao;
import com.jarvis.user.entity.FunctionAccess;
import com.jarvis.user.entity.FunctionPoint;
import com.jarvis.user.requestform.RoleFunctionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * Created by ZZF on 2018/3/21.
 */
@Service
public class SystemModuleService {
    @Autowired
    private FunctionAccessDao functionAccessDao;

    /**
     * 给角色分配功能清单
     * @param roleFunctionForm  功能清单列表
     */
    @Transactional
    public void createRoleFunction(RoleFunctionForm roleFunctionForm) {
        //删除该角色已经有的功能清单
        functionAccessDao.deleteByRoleId(roleFunctionForm.getRole().getId());
        List<FunctionPointDto> functionPointList =roleFunctionForm.getFunctionPointList();
        //遍历表单的功能，添加角色功能
        for (FunctionPoint functionPoint:functionPointList){
            FunctionAccess functionAccess=new FunctionAccess();
            functionAccess.setFunctionId(functionPoint.getId());
            functionAccess.setModuleId(functionPoint.getModuleId());
            functionAccess.setRoleId(roleFunctionForm.getRole().getId());
            functionAccessDao.save(functionAccess);
        }
    }
}
