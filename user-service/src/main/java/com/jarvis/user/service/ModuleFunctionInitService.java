package com.jarvis.user.service;

import com.jarvis.user.dao.DataAccessDao;
import com.jarvis.user.dao.FunctionPointDao;
import com.jarvis.user.dao.SystemModuleDao;
import com.jarvis.user.entity.DataAccess;
import com.jarvis.user.entity.FunctionPoint;
import com.jarvis.user.entity.SystemModule;
import com.jarvis.user.requestform.FunctionInitForm;
import com.jarvis.user.requestform.ModuleFunctionInitForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Created by ZZF on 2018/3/28.
 */
@Service
public class ModuleFunctionInitService {
    @Autowired
    private SystemModuleDao systemModuleDao;
    @Autowired
    private FunctionPointDao functionPointDao;
    @Autowired
    private DataAccessDao dataAccessDao;

    /**
     *
     * @param moduleFunctionInitForm 表单数据
     */
    @Transactional
    public void moduleFunctionInit(ModuleFunctionInitForm moduleFunctionInitForm){
        SystemModule systemModule=new SystemModule();
        BeanUtils.copyProperties(moduleFunctionInitForm,systemModule);
        systemModule.setEnabled(true);
        //插入模块
        systemModule=systemModuleDao.save(systemModule);
        for(FunctionInitForm functionInitForm:moduleFunctionInitForm.getFunctionInitFormList()){
            FunctionPoint functionPoint=new FunctionPoint();
            BeanUtils.copyProperties(functionInitForm,functionPoint);
            functionPoint.setEnabled(true);
            functionPoint.setModuleId(systemModule.getId());
            //插入功能点
            functionPoint= functionPointDao.save(functionPoint);
            for(String expr:functionInitForm.getDataAccess()){
                DataAccess dataAccess=new DataAccess();
                dataAccess.setFunctionId(functionPoint.getId());
                dataAccess.setAccessExpr(expr);
                //插入操作权限
                dataAccessDao.save(dataAccess);
            }
        }
    }
}
