package com.jarvis.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarvis.base.dto.PagedResult;
import com.jarvis.base.exception.BusinessApiException;
import com.jarvis.base.util.CurrentUserUtil;
import com.jarvis.user.Dto.OrgUserDetailDto;
import com.jarvis.user.constant.ErrorCode;
import com.jarvis.user.constant.RedisPrefix;
import com.jarvis.user.dao.*;
import com.jarvis.user.entity.OrgUser;
import com.jarvis.user.entity.OrganizationReference;
import com.jarvis.user.entity.SystemModule;
import com.jarvis.user.entity.User;
import com.jarvis.user.mapper.OrgUserMapper;
import com.jarvis.user.mapper.UserMapper;
import com.jarvis.user.query.UserListQuery;
import com.jarvis.user.requestform.UserCreateForm;
import com.jarvis.user.requestform.UserLoginForm;
import com.jarvis.user.service.UserService;
import com.jarvis.user.util.RegExpTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by jian on 18-3-9
 */

@Api(tags = "User", description = "用户接口")
@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private SystemModuleDao systemModuleDao;
    @Autowired
    private RedisTemplate<String, String> template;
    @Autowired
    private OrganizationReferenceDao organizationReferenceDao;
    @Autowired
    private OrgUserDao orgUserDao;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrgUserMapper orgUserMapper;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserMapper userMapper;
    /**
     * 用户登陆
     *
     * @param form     登陆提交表单
     * @param response 返回
     */
    @RequestMapping(method = RequestMethod.POST, value = "login")
    @ApiOperation("用户登录")
    public void login(@RequestBody UserLoginForm form,
                      HttpServletResponse response) {
        if (StringUtils.isBlank(form.getUsername())) {
            throw new BusinessApiException(ErrorCode.PARAM_BLANK, "用户名不能为空");
        }
        if (StringUtils.isBlank(form.getPassword())) {
            throw new BusinessApiException(ErrorCode.PARAM_BLANK, "密码不能为空");
        }
        if (StringUtils.isBlank(form.getModuleCode())) {
            throw new BusinessApiException(ErrorCode.PARAM_BLANK, "模块code不能为空");
        }
        if (StringUtils.isBlank(form.getUrl())) {
            throw new BusinessApiException(ErrorCode.PARAM_BLANK, "当前地址url不能为空");
        }
        //登陆验证
        User user = userService.loginUser(form.getUsername(), form.getPassword());
        Long agencyId = null;
        Long groupId = null;
        //如果没有机构id：1.判断是否只有一个机构，是就直接登陆，否则继续2
        //                2.判断是否有默认机构，是就以默认机构登陆，否则返回机构列表
        List<OrgUser> list = orgUserDao.findByUserIdAndEnabled(user.getId(), true);
        if (form.getOrgRefId() == null || form.getOrgRefId() == 0) {
            //存在用户机构关联关系
            //一个用户对应多个机构
            if (null != list && list.size() > 0) {
                if (list.size() == 1) {
                    //该用户只有一个机构信息,直接用该机构信息登入
                    OrganizationReference ref = organizationReferenceDao.getOne(list.get(0).getOrgRefId());
                    agencyId = ref.getOrgId();
                    groupId = ref.getGroupId();
                } else {
                    //遍历用户机构关联信息，查询是否含有默认机构
                    for (OrgUser orgUser : list) {
                        if (orgUser.getDefaultOrg()) {
                            OrganizationReference ref = organizationReferenceDao.getOne(list.get(0).getOrgRefId());
                            agencyId = ref.getOrgId();
                            groupId = ref.getGroupId();
                            break;
                        }
                    }
                    //没有默认机构，返回机构列表
                    if (agencyId == null || groupId == null) {
                        String responseJSONObject = null;
                        try {
                            List<OrgUserDetailDto> list1 = orgUserMapper.findOrgUserDetail(user.getId());
                            responseJSONObject = objectMapper.writeValueAsString(list1);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        response.setStatus(406);
                        response.setCharacterEncoding("UTF-8");
                        response.setContentType("application/json;  charset=utf-8");
                        PrintWriter out = null;
                        try {
                            out = response.getWriter();
                            out.append(responseJSONObject);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (out != null) {
                                out.close();
                            }
                        }
                    }

                }
            } else {
                throw new BusinessApiException(ErrorCode.ORG_ERROR, "机构信息异常");
            }
        } else {
            //有机构id:判断机构id是否和用户绑定机构匹配，匹配则用该机构id登陆，不匹配返回机构信息错误
            // 遍历用户机构关联信息，查询是否含有默认机构
            for (OrgUser orgUser : list) {
                if (orgUser.getOrgRefId() == form.getOrgRefId()) {
                    OrganizationReference ref = organizationReferenceDao.findOne(form.getOrgRefId());
                    if (null != ref) {
                        agencyId = ref.getOrgId();
                        groupId = ref.getGroupId();
                    }
                    break;
                }
            }
            //未找到匹配的机构
            if (agencyId == null || groupId == null) {
                throw new BusinessApiException(ErrorCode.ORG_ERROR, "机构信息异常");
            }
        }

        //登陆成功返回302重定向
        //根据模块号查询回掉地址
        SystemModule module = systemModuleDao.findByModuleCode(form.getModuleCode());
        if (null == module) {
            throw new BusinessApiException(ErrorCode.PARAM_ERROR, "模块号错误");
        }
        //生成唯一code返回给前端
        String code = String.valueOf(UUID.randomUUID());
        //生成token存储到redis
        String key = RedisPrefix.REDIS_KEY_PREFIX_TOKEN + code;
        String token = CurrentUserUtil.generateToken(user.getId(), user.getUsername(), agencyId, groupId, user.getRealName());
        template.opsForValue().set(key, token);
        template.expire(key, 10, TimeUnit.MINUTES);
        //之前访问url存储到redis
        String key1 = RedisPrefix.REDIS_KEY_PREFIX_URL + code;
        template.opsForValue().set(key1, form.getUrl());
        template.expire(key1, 10, TimeUnit.MINUTES);
        response.setStatus(302);
        response.setHeader("Location", module.getCallbackUrl() + "?code=" + code);
    }

    /**
     * code验证返回用户令牌token
     *
     * @param code 校验码
     * @return map
     */
    @RequestMapping(method = RequestMethod.GET, value = "query/getToken")
    @ApiOperation("code验证返回用户令牌token")
    public Map getToken(@ApiParam(required = true, name = "code", value = "校验code") @RequestParam("code") String code) {
        String token = template.opsForValue().get(RedisPrefix.REDIS_KEY_PREFIX_TOKEN + code);
        String url = template.opsForValue().get(RedisPrefix.REDIS_KEY_PREFIX_URL + code);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("token", token);
        resultMap.put("url", url);
        return resultMap;
    }

    /**
     * @param userId 用户id
     * @return List<OrgUser> 机构关系列表
     */
    @RequestMapping(method = RequestMethod.GET, value = "query/queryOrgUserList")
    @ApiOperation("根据用户id查询机构关联列表")
    public List<OrgUserDetailDto> queryOrgUserList(@ApiParam(required = true, name = "userId", value = "用户Id") @RequestParam("userId") long userId) {
        return orgUserMapper.findOrgUserDetail(userId);
    }

    /**
     * 新建用戶
     *
     * @param form 創建用戶表單
     */
    @RequestMapping(method = RequestMethod.POST, value = "command/createUser")
    @ApiOperation("新建用户")
    public void createUser(@RequestBody UserCreateForm form) {
        //验证表单信息
        validateUserCreateForm(form);
        userService.createUser(form);
    }
    /**
     * 编辑用戶
     *
     * @param form 創建用戶表單
     */
    @RequestMapping(method = RequestMethod.POST, value = "command/editUser")
    @ApiOperation("编辑用户")
    public void editUser(@RequestBody UserCreateForm form) {
        if(form.getId()==null){
            throw new BusinessApiException(ErrorCode.PARAM_BLANK,"用户id不能为空");
        }
        //验证表单信息
        validateUserEditForm(form);
        userService.editUser(form);
    }

    /**
     * 新建用户表单验证
     *
     * @param form 新建用户表单
     */
    private void validateUserCreateForm(UserCreateForm form) {
        if (StringUtils.isBlank(form.getUsername()))
            throw new BusinessApiException(ErrorCode.PARAM_BLANK, "用户名必填");
        if (StringUtils.isBlank(form.getPassword()))
            throw new BusinessApiException(ErrorCode.PARAM_BLANK, "密码必填");
        if (StringUtils.isBlank(form.getMobile())) {
            throw new BusinessApiException(ErrorCode.PARAM_BLANK, "手机号必填");
        } else {
            if (!RegExpTool.mobileMatch(form.getMobile()))
                throw new BusinessApiException(ErrorCode.PARAM_ERROR, "请输入正确的手机号码");
        }
        if (StringUtils.isBlank(form.getRealName()))
            throw new BusinessApiException(ErrorCode.PARAM_BLANK, "真实姓名必填");
        if (null == form.getDefaultOrgId()) {
            throw new BusinessApiException(ErrorCode.PARAM_BLANK, "默认机构必填");
        }
        User user=userDao.findByUsernameAndEnabled(form.getUsername(),true);
        if(null!=user){
            throw new BusinessApiException(ErrorCode.PARAM_ERROR, "用户名已存在");
        }
    }
    /**
     * 编辑用户表单验证
     *
     * @param form 新建用户表单
     */
    private void validateUserEditForm(UserCreateForm form) {
        if (StringUtils.isBlank(form.getUsername()))
            throw new BusinessApiException(ErrorCode.PARAM_BLANK, "用户名必填");
        if (StringUtils.isBlank(form.getMobile())) {
            throw new BusinessApiException(ErrorCode.PARAM_BLANK, "手机号必填");
        } else {
            if (!RegExpTool.mobileMatch(form.getMobile()))
                throw new BusinessApiException(ErrorCode.PARAM_ERROR, "请输入正确的手机号码");
        }
        if (StringUtils.isBlank(form.getRealName()))
            throw new BusinessApiException(ErrorCode.PARAM_BLANK, "真实姓名必填");
        if (null == form.getDefaultOrgId()) {
            throw new BusinessApiException(ErrorCode.PARAM_BLANK, "默认机构必填");
        }
    }
    /**
     * 查询用户列表
     *
     * @param userListQuery    查询表单
     * @return 分页数据
     */
    @RequestMapping(method = RequestMethod.POST, value = "query/queryUserList")
    @ApiOperation("用戶列表")
    public PagedResult<User> queryUserList(@RequestBody UserListQuery userListQuery) {
        List<User> userList=userMapper.findByParams(userListQuery,userListQuery.getPageNo()*userListQuery.getPageSize());
        Long totalCount=userMapper.countByParams(userListQuery);
        return new PagedResult<>(userList,totalCount,userListQuery.getPageNo(),userListQuery.getPageSize());
    }

    /**
     * 查询用户详情
     * @param userId  用户id
     * @return 用户表单
     */
    @RequestMapping(method = RequestMethod.GET, value = "query/queryUserDetail")
    @ApiOperation("用戶详情")
    public UserCreateForm queryUserDetail(@ApiParam(required = true, name = "userId", value = "用户Id") @RequestParam("userId") long userId){
    return userService.queryUserDetail(userId);
    }
}
