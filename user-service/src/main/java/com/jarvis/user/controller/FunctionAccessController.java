package com.jarvis.user.controller;

import com.jarvis.base.exception.BusinessApiException;
import com.jarvis.base.util.CurrentUserUtil;
import com.jarvis.user.Dto.FunctionAccessDto;
import com.jarvis.user.constant.ErrorCode;
import com.jarvis.user.mapper.FunctionAccessMapper;
import com.jarvis.user.util.RedisTemplateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;

    /**
     * @param moduleId 模块id
     * @param request request
     * @return 功能清单
     */
    @RequestMapping(method = RequestMethod.GET, value = "query/queryUserModuleFunctionAccess")
    @ApiOperation("查询用户某模块下的所有功能权限")
    public List<FunctionAccessDto> queryUserModuleFunctionAccess(
            @ApiParam(required = true, name = "moduleId", value = "模块Id") @RequestParam(value = "moduleId") Long moduleId
            , HttpServletRequest request) {
        Long userId = CurrentUserUtil.getCurrentUserId(request);
        if(userId==null)
            throw new BusinessApiException(ErrorCode.PARAM_BLANK,"请先登陆！");
        if(moduleId==null)
            throw new BusinessApiException(ErrorCode.PARAM_BLANK,"模块id不能为空");
        List<FunctionAccessDto> functionAccessDtos;
        //首先去redis查找该用户的权限，没有再去数据库查询
        functionAccessDtos=(List<FunctionAccessDto>) redisTemplateUtils.getCacheValue(request.getHeader("Authorization"));
        if (functionAccessDtos==null) {
            functionAccessDtos = functionAccessMapper.findUserModuleFunctionAccess(userId, moduleId);
            redisTemplateUtils.setCacheValueForTime(request.getHeader("Authorization"), functionAccessDtos, 5L, TimeUnit.MINUTES);
        }
        return functionAccessDtos;
    }
}
