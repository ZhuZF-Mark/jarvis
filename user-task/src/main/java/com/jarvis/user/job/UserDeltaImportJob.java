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

    @Scheduled(fixedDelay = 1000L * 60)
    private void doDeltaImport() {
        Date lastUpdateTime = userTaskTimeMapper.findLastUpdateTime();
        Date currentTime = new Date();
        while (currentTime.getTime() - lastUpdateTime.getTime() >= 1000L * 60 * 5) {
            userSyncService.doAgencySync(lastUpdateTime);
            userSyncService.doSupplierSync(lastUpdateTime);
            userSyncService.deltaImportOldDatasource(lastUpdateTime);
            userTaskTimeMapper.updateLastUpdateTime(lastUpdateTime);
            lastUpdateTime = DateUtils.addMinutes(lastUpdateTime, 5);
        }

    }
}
