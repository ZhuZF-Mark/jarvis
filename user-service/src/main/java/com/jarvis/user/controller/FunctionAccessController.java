package com.jarvis.user.controller;

import com.jarvis.base.exception.BusinessApiException;
import com.jarvis.base.util.CurrentUserUtil;
import com.jarvis.user.dto.FunctionAccessDto;
import com.jarvis.user.constant.ErrorCode;
import com.jarvis.user.dao.SystemModuleDao;
import com.jarvis.user.dao.UserDao;
import com.jarvis.user.entity.SystemModule;
import com.jarvis.user.entity.User;
import com.jarvis.user.mapper.FunctionAccessMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户权限相关接口
 * Created by ZZF on 2018/3/22.
 */
@Api(tags = "FunctionAccess", description = "用户权限列表接口")
@RestController
@RequestMapping("/FunctionAccess")
public class FunctionAccessController {
    @Autowired
    private FunctionAccessMapper functionAccessMapper;
//    @Autowired
//    private RedisTemplateUtils redisTemplateUtils;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SystemModuleDao systemModuleDao;

    /**
     * @param request request
     * @return 功能清单
     */
    @RequestMapping(method = RequestMethod.GET, value = "query/queryUserModuleFunctionAccess")
    @ApiOperation("查询用户某模块下的所有功能权限")
    public List<FunctionAccessDto> queryUserModuleFunctionAccess(HttpServletRequest request, @ApiParam( name = "moduleCode", value = "模块代码")@RequestParam(value="moduleCode",required = false) String moduleCode) {
        String username = CurrentUserUtil.getCurrentUsername(request);
        //根据php的用户名查用户
        User user=userDao.findByUsernameAndEnabled(username,true);
        Long userId=user==null?null:user.getId();
        if(userId==null)
            throw new BusinessApiException(ErrorCode.PARAM_BLANK,"请先登陆！");
        //查询模块信息
        SystemModule systemModule=null;
        if(!StringUtils.isEmpty(moduleCode)) {
             systemModule = systemModuleDao.findByModuleCode(moduleCode);
            if (systemModule == null)
                throw new BusinessApiException(ErrorCode.PARAM_BLANK, "模块码错误！");
        }
        List<FunctionAccessDto> functionAccessDtos;
        //首先去redis查找该用户的权限，没有再去数据库查询
//        functionAccessDtos=(List<FunctionAccessDto>) redisTemplateUtils.getCacheValue(request.getHeader("Authorization"));
//        if (functionAccessDtos==null) {
            functionAccessDtos = functionAccessMapper.findUserModuleFunctionAccess(userId, systemModule==null?null:systemModule.getId());
//            redisTemplateUtils.setCacheValueForTime(request.getHeader("Authorization"), functionAccessDtos, 5L, TimeUnit.MINUTES);
//        }
        return functionAccessDtos;
    }
}
