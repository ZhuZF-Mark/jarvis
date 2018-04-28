package com.jarvis.user.service;

import com.jarvis.user.TaskProperties;
import com.jarvis.user.dao.*;
import com.jarvis.user.dto.AgencyDto;
import com.jarvis.user.dto.SupplierDto;
import com.jarvis.user.dto.UserDto;
import com.jarvis.user.dto.UserRoleDto;
import com.jarvis.user.entity.*;
import com.jarvis.user.enums.OrgType;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;


/**
 * Created by Dong yangjian on 2018/3/13.
 */
@Component
public class UserSyncService {
    private static Logger log = LoggerFactory.getLogger(UserSyncService.class);

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private TaskProperties taskProperties;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrganizationReferenceDao organizationReferenceDao;

    @Autowired
    private OrgUserDao orgUserDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private UserRoleAssignmentDao userRoleAssignmentDao;

    @PostConstruct
    private void init() {
        DataSource dataSource = (DataSource) DataSourceBuilder.create()
                .driverClassName(taskProperties.getUserDatasource().getDriverClassName())
                .url(taskProperties.getUserDatasource().getUrl())
                .username(taskProperties.getUserDatasource().getUsername())
                .password(taskProperties.getUserDatasource().getPassword())
                .build();

        dataSource.setTestOnBorrow(true);
        dataSource.setValidationQuery("SELECT 1");

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

    }

    public void fullImportOldDatasource() {
        String sql = "select id from bus_usermaster";
        List<Integer> list = jdbcTemplate.getJdbcOperations().queryForList(sql, Integer.class);
        importUserInfo(list);
    }

    public void deltaImportOldDatasource(Date date) {
        String sql = "select id from bus_usermaster WHERE createdTime >= :deltatime OR updatedTime >= :deltatime ";
        Map<String, Object> map = new HashMap<>();
        map.put("deltatime", date);
        List<Integer> list = jdbcTemplate.queryForList(sql, map, Integer.class);
        importUserInfo(list);
    }

    public void doAgencySync(Date lastUpdateTime) {
        //机构增量同步
        String sql = "select id from bus_agencymaster WHERE createdTime >= :deltatime ";
        Map<String, Object> map = new HashMap<>();
        map.put("deltatime", lastUpdateTime);
        List<Long> list = jdbcTemplate.queryForList(sql, map, Long.class);
        if (null != list && !list.isEmpty()) {
            for (Long agencyId : list) {
                OrganizationReference orgAgency = getOrganizationReference(agencyId);
            }
        }
    }

    public void doSupplierSync(Date lastUpdateTime) {
        //供应商增量同步

        String sql = "select id from po_supliersmaster WHERE createdTime >= :deltatime ";
        Map<String, Object> map = new HashMap<>();
        map.put("deltatime", lastUpdateTime);
        List<Long> list = jdbcTemplate.queryForList(sql, map, Long.class);
        if (null != list && !list.isEmpty()) {
            for (Long suplierId : list) {
                OrganizationReference orgSupplier = getOrgReference(suplierId);
            }
        }
    }


    private void importUserInfo(List<Integer> list) {

        for (Integer id : list) {
            try {
                String sql = "SELECT " +
                        "agencyId,suplierId,userId,userPwd,trueName,mobile,useFlg," +
                        "memo,creator,editor,createdTime,updatedTime,defaultAgency " +
                        "FROM bus_usermaster WHERE id=:id";
                RowMapper<UserDto> rowMapper = new BeanPropertyRowMapper<>(UserDto.class);
                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                UserDto userDto = jdbcTemplate.queryForObject(sql, map, rowMapper);
                //封装并保存uesr对象，关系表对象，机构对象
                saveUserOrgRole(userDto);

            } catch (DataAccessException e) {
                log.info("新增或更新用户：{}失败！", id);
            }

        }
    }

    private void saveUserOrgRole(UserDto userDto) {
        //1.保存user对象
        User user = userDao.findByUsername(userDto.getUserId());
        if (user == null) {
            user = new User();
        }

        user.setUsername(userDto.getUserId());
        user.setRealName(userDto.getTrueName());
        user.setPassword(userDto.getUserPwd());
        user.setMobile(userDto.getMobile());
        user.setEnabled(userDto.getUseFlg());
        user.setMemo(userDto.getMemo());
        user = userDao.save(user);

        try {
            //2.机构账号创建机构关系
            if (userDto.getAgencyId() != null) {
                OrganizationReference orgAgency = getOrganizationReference(userDto.getAgencyId());
                OrgUser orgUser = saveOrgUser(userDto, user, orgAgency);
                getRoleAndSave(userDto, user, orgAgency, orgUser);
            }
            //3.供应商账号创建供应商关系
            if (userDto.getSuplierId() != null) {
                OrganizationReference orgSupplier = getOrgReference(userDto.getSuplierId());
                OrgUser orgUser = saveOrgUser(userDto, user, orgSupplier);
                getRoleAndSave(userDto, user, orgSupplier, orgUser);
            }
        } catch (EmptyResultDataAccessException e) {
            log.error("没有对应集团信息的用户:{}", userDto.getUserId());
        }
    }

    private OrganizationReference getOrgReference(Long suplierId) {
        //3.判断新库是否存在供应商，不存在则创建
        OrganizationReference orgSupplier = organizationReferenceDao.findByOrgIdAndOrgType(suplierId, OrgType.SUPPLIER);
        if (orgSupplier == null) {
            String sql = "select id,supliersName,supliersShortName,parentsupliersId,supliersCode," +
                    "useFlg from po_supliersmaster where id = :id";
            Map<String, Object> map = new HashMap<>();
            map.put("id", suplierId);
            SupplierDto supplierDto = jdbcTemplate.queryForObject(sql, map, new BeanPropertyRowMapper<>(SupplierDto.class));
            // 4..生成供应商 并保存
            orgSupplier = getOrgSupplier(supplierDto);
        }
        return orgSupplier;
    }

    private OrganizationReference getOrganizationReference(Long agencyId) {
        //1.判断新库是否存在机构，不存在则创建
        OrganizationReference orgAgency = organizationReferenceDao.findByOrgIdAndOrgType(agencyId, OrgType.AGENCY);
        if (orgAgency == null) {
            String sql = "select id,parentId,groupId,agencyCode,agencyName," +
                    "agencyShortName,useFlg from bus_agencymaster where id = :id";
            Map<String, Object> map = new HashMap<>();
            map.put("id", agencyId);
            AgencyDto agencyDto = jdbcTemplate.queryForObject(sql, map, new BeanPropertyRowMapper<>(AgencyDto.class));
            map.put("id", agencyDto.getGroupId());
            AgencyDto groupDto = jdbcTemplate.queryForObject(sql, map, new BeanPropertyRowMapper<>(AgencyDto.class));

            //2.生成机构 并保存
            orgAgency = getOrgAgency(agencyDto, groupDto);
        }
        return orgAgency;
    }

    /**
     * 获取老库中用户与角色的关联
     */
    private void getRoleAndSave(UserDto userDto, User user, OrganizationReference orgAgency, OrgUser orgUser) {

        String sql = "select roleId from bus_userrole where useFlg = 1 AND userId = :userId";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userDto.getUserId());
        List<Long> roleList = jdbcTemplate.queryForList(sql, map, Long.class);
        for (Long roleId : roleList) {
            saveRoleAndAssignment(user, orgAgency, orgUser, roleId);
        }
    }

    /**
     * 保存角色信息和关联信息
     */
    private void saveRoleAndAssignment(User user, OrganizationReference orgAgency, OrgUser orgUser, Long roleId) {
        String sql = "select roleName FROM bus_role WHERE roleId = :roleId";
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", roleId);
        String roleName = jdbcTemplate.queryForObject(sql, map, String.class);
        UserRole userRole = userRoleDao.findByNameAndGlobalRole(roleName,true);
        if(null==userRole){
            userRole= getUserRole(roleName);
        }
        UserRoleAssignment userRoleAssignment = userRoleAssignmentDao.findByOrgUserIdAndRoleId(orgUser.getId(), userRole.getId());
        if (userRoleAssignment == null) {
            userRoleAssignment = new UserRoleAssignment();
            userRoleAssignment.setUserId(user.getId());
            userRoleAssignment.setOrgUserId(orgUser.getId());
            userRoleAssignment.setRoleId(userRole.getId());
            userRoleAssignment.setOrgRefId(orgAgency.getId());
            userRoleAssignment.setEnabled(true);
            userRoleAssignment.setUnbound(true);
            userRoleAssignmentDao.save(userRoleAssignment);
        }

    }

    private OrgUser saveOrgUser(UserDto userDto, User user, OrganizationReference orgAgency) {
        OrgUser orgUser = orgUserDao.findByOrgRefIdAndUserId(orgAgency.getId(), user.getId());
        if (orgUser == null) {
            orgUser = new OrgUser();
            orgUser.setUserId(user.getId());
            orgUser.setOrgRefId(orgAgency.getId());
            if (userDto.getDefaultAgency() != 0
                    && Objects.equals(userDto.getDefaultAgency(), userDto.getAgencyId())) {
                orgUser.setDefaultOrg(true);
            } else {
                orgUser.setDefaultOrg(false);
            }
            orgUser.setEnabled(true);
            orgUser = orgUserDao.save(orgUser);
        }
        return orgUser;
    }

    private OrganizationReference getOrgSupplier(SupplierDto supplierDto) {
        OrganizationReference orgSupplier;
        orgSupplier = new OrganizationReference();
        orgSupplier.setGroupId(supplierDto.getId());
        orgSupplier.setGroupName(supplierDto.getSupliersName());
        orgSupplier.setOrgId(supplierDto.getId());
        orgSupplier.setOrgName(supplierDto.getSupliersName());
        orgSupplier.setOrgType(OrgType.SUPPLIER);
        return organizationReferenceDao.save(orgSupplier);

    }

    private OrganizationReference getOrgAgency(AgencyDto agencyDto, AgencyDto groupDto) {
        OrganizationReference orgAgency = new OrganizationReference();
        orgAgency.setGroupId(agencyDto.getGroupId());
        orgAgency.setGroupName(groupDto.getAgencyName());
        orgAgency.setOrgId(agencyDto.getId());
        orgAgency.setOrgName(agencyDto.getAgencyName());
        orgAgency.setOrgType(OrgType.AGENCY);
        return organizationReferenceDao.save(orgAgency);
    }

    private User getUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUserId());
        user.setRealName(userDto.getTrueName());
        user.setPassword(userDto.getUserPwd());
        user.setMobile(userDto.getMobile());
        user.setEnabled(userDto.getUseFlg());
        user.setMemo(userDto.getMemo());
        return userDao.save(user);
    }

    /**
     * 增量同步用户角色关联
     * @param lastUpdateTime 最后同步时间
     */
    public void doUserRoleSync(Date lastUpdateTime) {
        String sql = "select bu.userId,br.roleName from bus_userrole bu LEFT JOIN bus_role br on bu.roleId=br.roleId where bu.useFlg = 1 AND (bu.createdTime >= :deltatime OR bu.updatedTime >= :deltatime)";
        Map<String, Object> map = new HashMap<>();
        map.put("deltatime", lastUpdateTime);
        //查询最后时间点后新增或修改的用户角色关系
        List<UserRoleDto> roleList = jdbcTemplate.query(sql,map,new BeanPropertyRowMapper<>(UserRoleDto.class));
        for (UserRoleDto userRoleDto : roleList) {
            //查询出用户对应的机构或者供应商信息
            String sql1 = "SELECT " +
                    "agencyId,suplierId,userId,userPwd,trueName,mobile,useFlg," +
                    "memo,creator,editor,createdTime,updatedTime,defaultAgency " +
                    "FROM bus_usermaster WHERE userId=:userId";
            RowMapper<UserDto> rowMapper = new BeanPropertyRowMapper<>(UserDto.class);
            Map<String, Object> map1 = new HashMap<>();
            map1.put("userId", userRoleDto.getUserId());
            UserDto userDto = jdbcTemplate.queryForObject(sql1, map1, rowMapper);
            //查询新表用户信息
            User u=userDao.findByUsername(userRoleDto.getUserId());
            //查询新表角色信息
            UserRole userRole = userRoleDao.findByNameAndGlobalRole(userRoleDto.getRoleName(),true);
            if(null==userRole){
                userRole= getUserRole(userRoleDto.getRoleName());
            }
            try {
                //2.机构账号创建机构关系
                if (userDto.getAgencyId() != null) {
                    //查询新表机构信息
                    OrganizationReference angency= getOrganizationReference(userDto.getAgencyId());
                    //查询新表用户机构信息
                    OrgUser orgUser=orgUserDao.findByOrgRefIdAndUserId(angency.getId(),u.getId());
                    UserRoleAssignment userRoleAssignment = userRoleAssignmentDao.findByOrgUserIdAndRoleId(orgUser.getId(), userRole.getId());
                    saveUserRoleAssignment(userRole, orgUser, userRoleAssignment);
                }
                //3.供应商账号创建供应商关系
                if (userDto.getSuplierId() != null) {
                    //查询新表机构信息
                    OrganizationReference angency= getOrgReference(userDto.getSuplierId());
                    //查询新表用户机构信息
                    OrgUser orgUser=orgUserDao.findByOrgRefIdAndUserId(angency.getId(),u.getId());
                    UserRoleAssignment userRoleAssignment = userRoleAssignmentDao.findByOrgUserIdAndRoleId(orgUser.getId(), userRole.getId());
                    saveUserRoleAssignment(userRole, orgUser, userRoleAssignment);
                }
            } catch (EmptyResultDataAccessException e) {
                log.error("没有对应集团信息的用户:{}", userDto.getUserId());
            }
        }
    }

    private void saveUserRoleAssignment(UserRole userRole, OrgUser orgUser, UserRoleAssignment userRoleAssignment) {
        if (userRoleAssignment == null) {
            userRoleAssignment = new UserRoleAssignment();
            userRoleAssignment.setUserId(orgUser.getUserId());
            userRoleAssignment.setOrgUserId(orgUser.getId());
            userRoleAssignment.setRoleId(userRole.getId());
            userRoleAssignment.setOrgRefId(orgUser.getOrgRefId());
            userRoleAssignment.setEnabled(true);
            userRoleAssignment.setUnbound(true);
            userRoleAssignmentDao.save(userRoleAssignment);
        }
    }

    /**
     * 同步角色信息
     * @param lastUpdateTime 最后同步时间
     */
    public void doRoleSync(Date lastUpdateTime) {
        String sql = "select roleName FROM bus_role WHERE useFlg = 1 AND (createdTime >= :deltatime OR updatedTime >= :deltatime)";
        Map<String, Object> map = new HashMap<>();
        map.put("deltatime", lastUpdateTime);
        //查询新增的角色信息
        List<String> roleNames = jdbcTemplate.queryForList(sql, map, String.class);
        if (roleNames != null && !roleNames.isEmpty()) {
            for (String roleName : roleNames) {
                UserRole userRole = userRoleDao.findByNameAndGlobalRole(roleName,true);
                //如果新库没有该角色信息，进行插入操作
                if (userRole == null) {
                    userRole = getUserRole(roleName);
                }
            }
        }
    }

    /**
     * 插入角色信息
     * @param roleName 角色名
     * @return 返回插入后带id的角色实体
     */
    private UserRole getUserRole(String roleName) {
        UserRole userRole =new UserRole();
            userRole.setName(roleName);
            userRole.setGlobalRole(true);
            userRole.setEnabled(true);
            return userRoleDao.save(userRole);
    }


}
