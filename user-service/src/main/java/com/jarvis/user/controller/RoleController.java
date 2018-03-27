package com.jarvis.user.controller;

import com.jarvis.base.dto.PagedResult;
import com.jarvis.base.exception.BusinessApiException;
import com.jarvis.user.Dto.FunctionPointDto;
import com.jarvis.user.Dto.ModuleFunctionDto;
import com.jarvis.user.Dto.OrgRoleDto;
import com.jarvis.user.Dto.RoleDetailDto;
import com.jarvis.user.constant.ErrorCode;
import com.jarvis.user.dao.FunctionAccessDao;
import com.jarvis.user.dao.OrganizationReferenceDao;
import com.jarvis.user.dao.UserRoleDao;
import com.jarvis.user.entity.FunctionAccess;
import com.jarvis.user.entity.OrganizationReference;
import com.jarvis.user.entity.UserRole;
import com.jarvis.user.mapper.SystemModuleMapper;
import com.jarvis.user.mapper.UserRoleMapper;
import com.jarvis.user.query.RoleListQuery;
import com.jarvis.user.requestform.RoleForm;
import com.jarvis.user.requestform.RoleFunctionForm;
import com.jarvis.user.service.RoleService;
import com.jarvis.user.service.SystemModuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色相关接口
 * Created by ZZF on 2018/3/15.
 */
@Api(tags = "Role", description = "角色接口")
@RestController
@RequestMapping("/Role")
public class RoleController {
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private OrganizationReferenceDao organizationReferenceDao;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private SystemModuleMapper systemModuleMapper;
    @Autowired
    private FunctionAccessDao functionAccessDao;
    @Autowired
    private RoleService roleService;

    /**
     * 新建角色信息
     *
     * @param form 角色表单
     */
    @RequestMapping(method = RequestMethod.POST, value = "command/createRole")
    @ApiOperation("新建角色")
    public void createRole(@RequestBody RoleFunctionForm form) {
        validateForm(form);
        roleService.createRole(form);
    }

    /**
     * 查询机构下的角色列表-包含全局角色
     *
     * @param orgRefId 机构关联id
     * @return 角色列表
     */
    @RequestMapping(method = RequestMethod.GET, value = "query/queryOrgRoleList")
    @ApiOperation("查询机构下的角色列表-包含全局角色")
    public List<UserRole> queryOrgRoleList(@ApiParam(required = true, name = "orgRefId", value = "机构关联id") @RequestParam("orgRefId") Long orgRefId) {
        return userRoleDao.findByOrgRefIdOrGlobalRole(orgRefId, true);
    }

    /**
     * 分页查询角色列表
     *
     * @param roleListQuery 查询参数
     * @return 分页数据
     */
    @RequestMapping(method = RequestMethod.POST, value = "query/queryRoleList")
    @ApiOperation("分页查询角色列表")
    public PagedResult<OrgRoleDto> queryRoleList(@RequestBody RoleListQuery roleListQuery) {
        List<OrgRoleDto> dtos = userRoleMapper.findOrgUser(roleListQuery, roleListQuery.getPageSize() * roleListQuery.getPageNo());
        Long count = userRoleMapper.countOrgUser(roleListQuery);
        int totalPage = (int) (count / roleListQuery.getPageSize() + (count % roleListQuery.getPageSize() > 0 ? 1 : 0));
        return new PagedResult<>(dtos, count, roleListQuery.getPageNo(), totalPage);
    }

    /**
     * @param roleId 角色id
     * @param open   开启-关闭
     */
    @RequestMapping(method = RequestMethod.GET, value = "query/roleSwitch")
    @ApiOperation("角色开启-关闭")
    public void roleSwitch(@ApiParam(required = true, name = "roleId", value = "角色id") @RequestParam("roleId") Long roleId,
                           @ApiParam(required = true, name = "open", value = "开启") @RequestParam("open") Boolean open) {
        UserRole role = userRoleDao.findOne(roleId);
        role.setEnabled(open);
        userRoleDao.save(role);
    }

    /**
     * 角色表单数据验证
     *
     * @param form 角色提交表单
     */
    private void validateForm(RoleFunctionForm form) {
        if (StringUtils.isBlank(form.getRole().getName()))
            throw new BusinessApiException(ErrorCode.PARAM_BLANK, "角色名必填");
        if (form.getRole().getGlobalRole() == null)
            throw new BusinessApiException(ErrorCode.PARAM_BLANK, "是否全局必填");
        //设置全局角色，机构角色不生效
        if (form.getRole().getGlobalRole()) {
            form.getRole().setOrgRefId(null);
        } else {
            if (form.getRole().getOrgRefId() == null) {
                throw new BusinessApiException(ErrorCode.PARAM_BLANK, "机构必填");
            } else {
                //判断机构信息是否合法
                OrganizationReference orf = organizationReferenceDao.findOne(form.getRole().getOrgRefId());
                if (orf == null)
                    throw new BusinessApiException(ErrorCode.PARAM_ERROR, "非法机构id");
            }
        }
    }

    /**
     * 角色详情
     *
     * @param roleId 角色id
     * @return 角色详情dto
     */
    @RequestMapping(method = RequestMethod.GET, value = "query/queryRoleDetail")
    @ApiOperation("角色详情")
    public RoleDetailDto queryRoleDetail(@ApiParam(required = true, name = "roleId", value = "角色id") @RequestParam("roleId") Long roleId) {
        RoleDetailDto form = userRoleMapper.findRoleDetail(roleId);
        if (form == null) {
            throw new BusinessApiException(ErrorCode.PARAM_ERROR, "非法角色Id");
        }
        //查询全局功能权限
        List<ModuleFunctionDto> moduleFunctionDtoList = systemModuleMapper.findAllModuleFunction();
        //查询该角色拥有的模块功能点权限
        List<FunctionAccess> functionAccessList = functionAccessDao.findByRoleId(roleId);
        for (ModuleFunctionDto moduleFunctionDto : moduleFunctionDtoList) {
            List<FunctionPointDto> functionPointList = moduleFunctionDto.getFunctionPointList();
            for (FunctionPointDto point : functionPointList) {
                point.setChecked(false);
                //遍历已经拥有的功能权限
                for (FunctionAccess functionAccess : functionAccessList) {
                    //如果该功能已经拥有，则标记为checked
                    if (functionAccess != null && point != null && functionAccess.getFunctionId().equals(point.getId())) {
                        point.setChecked(true);
                    }
                }
            }
        }
        form.setModuleFunctionDtoList(moduleFunctionDtoList);
        return form;
    }

}
