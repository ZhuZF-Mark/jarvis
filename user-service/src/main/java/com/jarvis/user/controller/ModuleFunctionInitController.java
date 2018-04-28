package com.jarvis.user.controller;

import com.jarvis.user.requestform.ModuleFunctionInitForm;
import com.jarvis.user.service.ModuleFunctionInitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能权限初始化导入
 * Created by ZZF on 2018/3/28.
 */
@Api(tags = "ModuleFunctionInit", description = "功能权限初始化导入")
@RestController
@RequestMapping("/ModuleFunctionInit")
public class ModuleFunctionInitController {
    @Autowired
    private ModuleFunctionInitService moduleFunctionInitService;

    @RequestMapping(method = RequestMethod.POST, value = "command/moduleFunctionInit")
    @ApiOperation("导入模块功能权限")
    public void moduleFunctionInit(@RequestBody ModuleFunctionInitForm moduleFunctionInitForm) {
        moduleFunctionInitService.moduleFunctionInit(moduleFunctionInitForm);
    }

}
