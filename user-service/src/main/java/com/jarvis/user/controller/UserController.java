package com.jarvis.user.controller;

import com.jarvis.base.dto.PagedResult;
import com.jarvis.base.exception.BusinessApiException;
import com.jarvis.base.util.CurrentUserUtil;
import com.jarvis.user.dto.OrgUserDetailDto;
import com.jarvis.user.dto.OrgUserDto;
import com.jarvis.user.dto.UserListDto;
import com.jarvis.user.constant.ErrorCode;
import com.jarvis.user.constant.RedisPrefix;
import com.jarvis.user.dao.*;
import com.jarvis.user.entity.OrganizationReference;
import com.jarvis.user.entity.User;
import com.jarvis.user.mapper.OrgUserMapper;
import com.jarvis.user.mapper.OrganizationReferenceMapper;
import com.jarvis.user.mapper.UserMapper;
import com.jarvis.user.query.UserListQuery;
import com.jarvis.user.requestform.UserCreateForm;
import com.jarvis.user.requestform.UserLoginForm;
import com.jarvis.user.requestform.WxBindForm;
import com.jarvis.user.service.UserService;
import com.jarvis.user.util.*;
import com.jarvis.user.wx.WxAccessToken;
import com.jarvis.user.wx.WxBaseUserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
    private RedisTemplateUtils template;
    @Autowired
    private OrgUserMapper orgUserMapper;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrganizationReferenceMapper organizationReferenceMapper;
    @Autowired
    private WXUtil wxUtil;

    /**
     * 用户登陆，返回code
     *
     * @param form 登陆提交表单
     */
    @RequestMapping(method = RequestMethod.POST, value = "getLoginCode")
    @ApiOperation("用户登录获取code")
    public Map login(@RequestBody UserLoginForm form) {
        try {
            if (StringUtils.isBlank(form.getUsername())) {
                return ResultMap.failResult(null, "手机号不能为空", ErrorCode.PARAM_BLANK);
            }
            if (StringUtils.isBlank(form.getPassword())) {
                return ResultMap.failResult(null, "密码不能为空", ErrorCode.PARAM_BLANK);
            }
            if (StringUtils.isBlank(form.getModuleCode())) {
                return ResultMap.failResult(null, "模块code不能为空", ErrorCode.PARAM_BLANK);
            }
            if (StringUtils.isBlank(form.getCallbackUrl())) {
                return ResultMap.failResult(null, "回调地址不能为空", ErrorCode.PARAM_BLANK);
            }
            List<User> user = userService.loginUser(form.getUsername(), form.getPassword());
            if (user == null || user.size() == 0) {
                return ResultMap.failResult(null, "用户名密码错误", ErrorCode.USER_OR_PASSWORD_ERROR);
            }
            //生成唯一code返回给前端
            String code = "jws" + CodeUtil.getUuidCode();
            //生成token存储到redis
            String key = RedisPrefix.REDIS_KEY_PREFIX_TOKEN + code;
            //将表单信息缓存到redis
            template.setCacheValueForTime(key, form, 10, TimeUnit.MINUTES);
            return ResultMap.successResult(code);
        } catch (Exception e) {
            return ResultMap.failResult(null, "用户中心系统异常", ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * code验证返回用户令牌token
     *
     * @param code 校验码
     * @return map
     */
    @RequestMapping(method = RequestMethod.GET, value = "query/getToken")
    @ApiOperation("code验证返回用户令牌token")
    public Map getToken(@ApiParam(required = true, name = "code", value = "校验code") @RequestParam("code") String code) throws JSONException {
        //非微信登陆入口
        if (code.startsWith("jws")) {
            String key = RedisPrefix.REDIS_KEY_PREFIX_TOKEN + code;
            UserLoginForm loginForm = (UserLoginForm) template.getCacheValue(key);
            //查询用户的机构列表
            List<OrganizationReference> orgList = organizationReferenceMapper.findByUserMobile(loginForm.getUsername(), MD5Utils.getMD5String(loginForm.getPassword()));
            String token = getToken(orgList, loginForm, code);
            return ResultMap.successResult(token);
        } else
        //微信入口
        {
            //获取微信access_token
            WxAccessToken accessToken = wxUtil.getAccessToken(code);
            if (accessToken.getOpenid() == null && !accessToken.getErrcode().equals("")) {
                return ResultMap.failResult(null, accessToken.getErrmsg(), String.valueOf(accessToken.getErrcode()));
            }
            //将登陆流水号存入redis缓存
            template.setCacheValueForTime(code, accessToken, 10, TimeUnit.MINUTES);
            System.out.println("accessToken:" + accessToken.toString());
            //获取用户信息
            WxBaseUserInfo userInfo = wxUtil.getUserInfo(accessToken.getAccess_token(), accessToken.getOpenid());
            if (userInfo.getOpenid().equals("")) {
                return ResultMap.failResult(null, userInfo.getErrmsg(), String.valueOf(userInfo.getErrcode()));
            }
            System.out.println("userInfo:" + userInfo.toString());
            //根据unionId查询本系统user
            List<OrganizationReference> orgList = organizationReferenceMapper.findByWxUnionId(userInfo.getUnionid());
            String token = getTokenByUnionId(orgList, userInfo.getUnionid(), code);
            if (token == null) {
                return ResultMap.failResult(code, "微信未绑定", ErrorCode.WX_NOT_BIND);
            }
            return ResultMap.successResult(token);
        }
    }

    /**
     * 绑定微信id
     *
     * @param wxBindForm 表单
     * @return token
     */
    @RequestMapping(method = RequestMethod.GET, value = "command/bindWx")
    @ApiOperation("绑定微信id")
    public Map bindWxId(@RequestBody WxBindForm wxBindForm) {
        try {
            if (StringUtils.isBlank(wxBindForm.getMobile())) {
                return ResultMap.failResult(null, "手机号不能为空", ErrorCode.PARAM_BLANK);
            }
            if (StringUtils.isBlank(wxBindForm.getPassword())) {
                return ResultMap.failResult(null, "密码不能为空", ErrorCode.PARAM_BLANK);
            }
            if (StringUtils.isBlank(wxBindForm.getCode())) {
                return ResultMap.failResult(null, "登陆流水号不能为空", ErrorCode.PARAM_BLANK);
            }
            //绑定用户微信到手机号
            userMapper.updateUserWxUnionId(wxBindForm);
            WxAccessToken accessToken = (WxAccessToken) template.getCacheValue(wxBindForm.getCode());
            WxBaseUserInfo userInfo = wxUtil.getUserInfo(accessToken.getAccess_token(), accessToken.getOpenid());
            if (userInfo.getOpenid().equals("")) {
                return ResultMap.failResult(null, userInfo.getErrmsg(), String.valueOf(userInfo.getErrcode()));
            }
            //根据unionId查询本系统user
            List<OrganizationReference> orgList = organizationReferenceMapper.findByWxUnionId(userInfo.getUnionid());
            String token = getTokenByUnionId(orgList, userInfo.getUnionid(), wxBindForm.getCode());
            if (token == null) {
                return ResultMap.failResult(wxBindForm.getCode(), "微信未绑定成功！", ErrorCode.WX_NOT_BIND);
            }
            return ResultMap.successResult(token);
        } catch (Exception e) {
            return ResultMap.failResult(null, "用户中心系统异常", ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 选择机构登陆
     */
    @RequestMapping(method = RequestMethod.GET, value = "query/getAccesstoken")
    @ApiOperation("获取准确的token")
    public Map getAccesstoken(@ApiParam(required = true, name = "token", value = "token") @RequestParam("token") String token,
                              @ApiParam(required = true, name = "agencyId", value = "机构id") @RequestParam("agencyId") Long agencyId) {
        try {
      String code =TokenUtil.getCode(token);
        if(code.startsWith("jws")){
            String key = RedisPrefix.REDIS_KEY_PREFIX_TOKEN + code;
            UserLoginForm loginForm = (UserLoginForm) template.getCacheValue(key);
            //根据手机号，密码，机构，确定一个用户
          OrgUserDto orgUserDto= userMapper.selectOrgUserByMobile(loginForm.getUsername(), MD5Utils.getMD5String(loginForm.getPassword()),agencyId);
            token = CurrentUserUtil.generateToken(orgUserDto.getId(),orgUserDto.getUsername(),orgUserDto.getOrganizationReference().getOrgId(),orgUserDto.getOrganizationReference().getGroupId(),orgUserDto.getRealName(),orgUserDto.getMobile());
            return ResultMap.successResult(token);
        }else{
            //根据code去缓存查询微信accessToken
            WxAccessToken accessToken = (WxAccessToken) template.getCacheValue(code);
            //根据accessToken查询用户信息
            WxBaseUserInfo userInfo = wxUtil.getUserInfo(accessToken.getAccess_token(), accessToken.getOpenid());
            if (userInfo.getOpenid().equals("")) {
                return ResultMap.failResult(null, userInfo.getErrmsg(), String.valueOf(userInfo.getErrcode()));
            }
            OrgUserDto orgUserDto= userMapper.selectOrgUserByUnionId(userInfo.getUnionid(),agencyId);
            token = CurrentUserUtil.generateToken(orgUserDto.getId(),orgUserDto.getUsername(),orgUserDto.getOrganizationReference().getOrgId(),orgUserDto.getOrganizationReference().getGroupId(),orgUserDto.getRealName(),orgUserDto.getMobile());
            return ResultMap.successResult(token);
        }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMap.failResult(null, "用户中心系统异常", ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 用户名密码获取token
     *
     * @param orgList   机构列表
     * @param loginForm 登陆表单
     * @return token
     */
    public String getToken(List<OrganizationReference> orgList, UserLoginForm loginForm, String code) {
        String token = null;
        if (orgList != null && orgList.size() > 0) {
            //多个机构，返回机构列表
            if (orgList.size() > 1) {
                List<Long> agencyIds = new ArrayList<>();
                for (OrganizationReference organizationReference : orgList) {
                    agencyIds.add(organizationReference.getOrgId());
                }
                //生成token
                token = TokenUtil.generateToken(agencyIds.toString(), loginForm.getUsername(), code);
            } else {
                //返回单个机构的token
                List<User> user = userService.loginUser(loginForm.getUsername(), loginForm.getPassword());
                token = CurrentUserUtil.generateToken(user.get(0).getId(), user.get(0).getUsername(), orgList.get(0).getOrgId(), orgList.get(0).getGroupId(), user.get(0).getRealName(), user.get(0).getMobile());
            }
        }
        return token;
    }

    /**
     * unionID密码获取token
     *
     * @param orgList 机构列表
     * @return token
     */
    public String getTokenByUnionId(List<OrganizationReference> orgList, String unionId, String code) {
        String token = null;
        List<User> userlist = userMapper.findByUnionId(unionId);
        if (orgList != null && orgList.size() > 0) {
            //多个机构，返回机构列表
            if (orgList.size() > 1) {
                List<Long> agencyIds = new ArrayList<>();
                for (OrganizationReference organizationReference : orgList) {
                    agencyIds.add(organizationReference.getOrgId());
                }
                //生成token
                token = TokenUtil.generateToken(agencyIds.toString(), userlist.get(0).getMobile(), code);
            } else {
                //返回单个机构的token
                token = CurrentUserUtil.generateToken(userlist.get(0).getId(), userlist.get(0).getUsername(), orgList.get(0).getOrgId(), orgList.get(0).getGroupId(), userlist.get(0).getRealName(), userlist.get(0).getMobile());
            }
        }
        return token;
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
        if (form.getId() == null) {
            throw new BusinessApiException(ErrorCode.PARAM_BLANK, "用户id不能为空");
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
//        if (null == form.getDefaultOrgId()) {
//            throw new BusinessApiException(ErrorCode.PARAM_BLANK, "默认机构必填");
//        }
        User user = userDao.findByUsernameAndEnabled(form.getUsername(), true);
        if (null != user) {
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
//        if (null == form.getDefaultOrgId()) {
//            throw new BusinessApiException(ErrorCode.PARAM_BLANK, "默认机构必填");
//        }
    }

    /**
     * 查询用户列表
     *
     * @param userListQuery 查询表单
     * @return 分页数据
     */
    @RequestMapping(method = RequestMethod.POST, value = "query/queryUserList")
    @ApiOperation("用戶列表")
    public PagedResult<UserListDto> queryUserList(@RequestBody UserListQuery userListQuery) {
        List<UserListDto> userList = userMapper.findByParams(userListQuery, userListQuery.getPageNo() * userListQuery.getPageSize());
        Long totalCount = userMapper.countByParams(userListQuery);
        return new PagedResult<>(userList, totalCount, userListQuery.getPageNo(), userListQuery.getPageSize());
    }

    /**
     * 查询用户详情
     *
     * @param userId 用户id
     * @return 用户表单
     */
    @RequestMapping(method = RequestMethod.GET, value = "query/queryUserDetail")
    @ApiOperation("用戶详情")
    public UserCreateForm queryUserDetail(@ApiParam(required = true, name = "userId", value = "用户Id") @RequestParam("userId") long userId) {
        return userService.queryUserDetail(userId);
    }
}
