package com.jarvis.user.controller;

import com.jarvis.user.dao.OrganizationReferenceDao;
import com.jarvis.user.entity.OrganizationReference;
import com.jarvis.user.mapper.OrganizationReferenceMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ZZF on 2018/3/15.
 */
@Api(tags = "OrganizationReference", description = "集团机构相关接口")
@RestController
@RequestMapping("/OrganizationReference")
public class OrganizationReferenceController {
    @Autowired
    private OrganizationReferenceMapper organizationReferenceMapper;

    /**
     * @param orgName 机构全称
     * @return 机构列表
     */
    @RequestMapping(method = RequestMethod.GET, value = "query/queryOrgRefList")
    @ApiOperation("机构列表")
    public List<OrganizationReference> queryOrgRefList(@ApiParam(name = "orgName", value = "机构全称") @RequestParam(value="orgName",required = false) String orgName){
        return organizationReferenceMapper.findByOrgNameLike(orgName);
    }

}
