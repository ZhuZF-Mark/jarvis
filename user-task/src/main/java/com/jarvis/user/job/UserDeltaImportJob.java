package com.jarvis.user.job;

import com.jarvis.user.mapper.UserTaskTimeMapper;
import com.jarvis.user.service.UserSyncService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Dong yangjian on 2018/3/15.
 */
@Component
public class UserDeltaImportJob {

    @Autowired
    private UserSyncService userSyncService;

    @Autowired
    private UserTaskTimeMapper userTaskTimeMapper;

    @Scheduled(fixedDelay = 1000L * 30)
    private void doDeltaImport() {
        Date lastUpdateTime = userTaskTimeMapper.findLastUpdateTime();
        try {
            Date currentTime = new Date();
//        while (currentTime.getTime() - lastUpdateTime.getTime() >= 1000L * 60 * 5) {
            userTaskTimeMapper.updateLastUpdateTime(currentTime);
            userSyncService.doAgencySync(lastUpdateTime);
            userSyncService.doSupplierSync(lastUpdateTime);
            userSyncService.deltaImportOldDatasource(lastUpdateTime);
            //同步角色信息
            userSyncService.doRoleSync(lastUpdateTime);
            //同步用户角色
            userSyncService.doUserRoleSync(lastUpdateTime);
        }catch (Exception e){
            e.printStackTrace();
            userTaskTimeMapper.updateLastUpdateTime(lastUpdateTime);
        }
        }

//    }
}
