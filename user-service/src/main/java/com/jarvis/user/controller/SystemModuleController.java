package com.jarvis.user.controller;

import com.jarvis.user.Dto.FunctionPointDto;
import com.jarvis.user.Dto.ModuleFunctionDto;
import com.jarvis.user.dao.FunctionAccessDao;
import com.jarvis.user.entity.FunctionAccess;
import com.jarvis.user.entity.FunctionPoint;
import com.jarvis.user.mapper.SystemModuleMapper;
import com.jarvis.user.requestform.RoleFunctionForm;
import com.jarvis.user.service.SystemModuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统模块功能点相关接口
 * Created by ZZF on 2018/3/20.
 */
@Api(tags = "Module", description = "系统模块")
@RestController
@RequestMapping("/Module")
public class SystemModuleController {
    @Autowired
    private SystemModuleMapper systemModuleMapper;
    @Autowired
    private SystemModuleService systemModuleService;

    /**
     * 查询所有模块和模块下的功能点
     *
     * @return 功能和功能点列表
     */
    @RequestMapping(method = RequestMethod.GET, value = "query/queryAllModuleFunction")
    @ApiOperation("查询所有模块和模块下的功能点")
    public List<ModuleFunctionDto> queryAllModuleFunction() {
        return systemModuleMapper.findAllModuleFunction();
    }
//    /**
//     * 查询某角色下拥有的功能清单
//     */
//    @RequestMapping(method = RequestMethod.GET, value = "query/queryRoleModuleFunction")
//    @ApiOperation("查询某角色下模块和模块下的功能点")
//    public List<ModuleFunctionDto> queryARoleModuleFunction(@ApiParam(required = true, name = "roleId", value = "角色Id") @RequestParam("roleId") long roleId) {
//        //查询全局功能权限
//        List<ModuleFunctionDto> moduleFunctionDtoList=systemModuleMapper.findAllModuleFunction();
//        //查询该角色拥有的模块功能点权限
//        List<FunctionAccess> functionAccessList=functionAccessDao.findByRoleId(roleId);
//        for (ModuleFunctionDto moduleFunctionDto:moduleFunctionDtoList){
//            List<FunctionPointDto> functionPointList =moduleFunctionDto.getFunctionPointList();
//            for(FunctionPointDto point:functionPointList){
//                //遍历已经拥有的功能权限
//                for(FunctionAccess functionAccess:functionAccessList){
//                    //如果该功能已经拥有，则标记为checked
//                    if(functionAccess!=null&&point!=null&&functionAccess.getFunctionId().equals(point.getId())){
//                      point.setChecked(true);
//                    }else{
//                        assert point != null;
//                        point.setChecked(false);
//                    }
//                }
//            }
//        }
//        return moduleFunctionDtoList;
//    }

    /**
     *给角色配置功能权限
     * @param roleFunctionForm 功能权限Form表单
     */
    @RequestMapping(method = RequestMethod.POST, value = "command/createRoleFunction")
    @ApiOperation("给角色配置功能权限")
    public void createRoleFunction(@RequestBody RoleFunctionForm roleFunctionForm){
        systemModuleService.createRoleFunction(roleFunctionForm);
    }
}
