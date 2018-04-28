package com.jarvis.user.service;

import com.jarvis.base.exception.BusinessApiException;
import com.jarvis.user.dto.OrgUserDetailDto;
import com.jarvis.user.dto.UserOrgRoleDto;
import com.jarvis.user.constant.ErrorCode;
import com.jarvis.user.dao.OrgUserDao;
import com.jarvis.user.dao.UserDao;
import com.jarvis.user.dao.UserRoleAssignmentDao;
import com.jarvis.user.dao.UserRoleDao;
import com.jarvis.user.entity.OrgUser;
import com.jarvis.user.entity.User;
import com.jarvis.user.entity.UserRole;
import com.jarvis.user.entity.UserRoleAssignment;
import com.jarvis.user.mapper.OrgUserMapper;
import com.jarvis.user.mapper.UserMapper;
import com.jarvis.user.mapper.UserRoleAssignmentMapper;
import com.jarvis.user.requestform.OrganizationReferenceForm;
import com.jarvis.user.requestform.UserCreateForm;
import com.jarvis.user.requestform.UserRoleForm;
import com.jarvis.user.util.MD5Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户模块业务
 * Created by ZZF on 2018/3/12.
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrgUserDao orgUserDao;
    @Autowired
    private UserRoleAssignmentDao userRoleAssignmentDao;
    @Autowired
    private OrgUserMapper orgUserMapper;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private UserRoleAssignmentMapper userRoleAssignmentMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登陆
     *
     * @param username 用户名
     * @param password 密码
     * @return User user实体
     */
    public List<User> loginUser(String username, String password) {
        //对明文密码进行加密
        String md5Password = MD5Utils.getMD5String(password);
        //根据用户名密码查询用户
        List<User> user = userDao.findByMobileAndPasswordAndEnabled(username, md5Password, true);
        return user;
    }

    /**
     * @param form 用户创建表单
     */
    @Transactional
    public void createUser(UserCreateForm form) {
        User user = new User();
        BeanUtils.copyProperties(form, user);
        user.setPassword(MD5Utils.getMD5String(form.getPassword()));
        user.setEnabled(true);
        //插入user表
        user=userDao.save(user);
        createUser(form,user);
    }

    /**
     * 查询用户详情
     * @param userId 用户Id
     * @return 用户详情包装类
     */
    public UserCreateForm queryUserDetail(Long userId){
        UserCreateForm form=new UserCreateForm();
        //查询用户基本信息
        User user=userMapper.queryById(userId);
        BeanUtils.copyProperties(user,form);
        form.setPassword(null);
        //查询用户的机构列表
        List<OrgUserDetailDto> orgUserDetailDtos=orgUserMapper.findOrgUserDetail(userId);
        List<OrganizationReferenceForm> organizationReferenceFormList =new ArrayList<>();
        //查询该用户的已经拥有的角色
        List<UserOrgRoleDto> userOrgRoleDtos=userRoleAssignmentMapper.findUserOrgRoleList(userId);
        //查询机构下的全部角色列表
        for(OrgUserDetailDto dto:orgUserDetailDtos) {
            //转换
            OrganizationReferenceForm organizationReferenceForm=new OrganizationReferenceForm();
            BeanUtils.copyProperties(dto,organizationReferenceForm);
            List<UserRoleForm> userRoleFormList=new ArrayList<>();
            //如果是默认机构，返回默认机构id
            if(dto.getDefaultOrg()){
                form.setDefaultOrgId(dto.getId());
             }
            //查询机构下的所有角色
            List<UserRole> userRoles = userRoleDao.findByOrgRefIdOrGlobalRoleAndEnabled(dto.getId(), true,true);
            for(UserRole ur:userRoles){
                UserRoleForm urf=new UserRoleForm();
                BeanUtils.copyProperties(ur,urf);
                //比对用户已有的角色和所有的角色，对已有的角色进行标记
                   for(UserOrgRoleDto userOrgRoleDto: userOrgRoleDtos) {
                       if (userOrgRoleDto.getOrgId().equals(dto.getOrgId()) && userOrgRoleDto.getRoleId().equals(ur.getId())) {
                           urf.setChecked(true);
                           urf.setFromDate(userOrgRoleDto.getFromDate());
                           urf.setEndDate(userOrgRoleDto.getEndDate());
                           urf.setUnbound(userOrgRoleDto.getUnbound());
                           break;
                       }
                   }
                userRoleFormList.add(urf);
            }
            //添加角色列表到表单
            organizationReferenceForm.setUserRoleFormList(userRoleFormList);
            organizationReferenceFormList.add(organizationReferenceForm);
        }
        //添加机构列表到表单
        form.setOrganizationReferenceFormList(organizationReferenceFormList);
        return form;
    }
    /**
     * 编辑用户信息
     * @param form 用户信息表单
     */
    @Transactional
    public void editUser(UserCreateForm form) {
        User user = userDao.findOne(form.getId());
        if(form.getPassword()!=null){
        user.setPassword(MD5Utils.getMD5String(form.getPassword()));
        }
        user.setMemo(form.getMemo());
        user.setMobile(form.getMobile());
        user.setRealName(form.getRealName());
        user.setEnabled(true);
        //update user表
        user=userDao.save(user);
        //删除用户机构关系
        orgUserMapper.deleteByUserId(user.getId());
        //删除用户角色关系
        userRoleAssignmentMapper.deleteByUserId(user.getId());
        createUser(form,user);
    }
    /**
     * 公共方法-新建用户角色之间关系
     * @param form 用户信息表单
     * @param user  用户信息
     */
    private void createUser(UserCreateForm form, User user){
        //插入用户机构关联关系表
        for(OrganizationReferenceForm orf:form.getOrganizationReferenceFormList()){
            OrgUser ou=new OrgUser();
            ou.setUserId(user.getId());
            ou.setOrgRefId(orf.getId());
            if(orf.getId().equals(form.getDefaultOrgId())){
                ou.setDefaultOrg(true);
            }else{
                ou.setDefaultOrg(false);
            }
            ou.setEnabled(true);
            ou= orgUserDao.save(ou);
            //遍历角色数据,给用户添加角色
            for (UserRoleForm urf:orf.getUserRoleFormList()){
                UserRoleAssignment ura=new UserRoleAssignment();
                ura.setUserId(user.getId());
                ura.setOrgRefId(ou.getOrgRefId());
                if(!urf.getUnbound()){
                    if(urf.getFromDate().after(urf.getEndDate())){
                        throw new BusinessApiException(ErrorCode.PARAM_ERROR,"开始时间不能大于结束时间!");
                    }
                    ura.setFromDate(urf.getFromDate());
                    ura.setEndDate(urf.getEndDate());
                }
                ura.setUnbound(urf.getUnbound());
                ura.setRoleId(urf.getId());
                ura.setOrgUserId(ou.getId());
                ura.setEnabled(true);
                userRoleAssignmentDao.save(ura);
            }
    }
    }
}
