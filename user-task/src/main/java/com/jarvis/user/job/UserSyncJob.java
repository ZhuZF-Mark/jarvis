package com.jarvis.user.job;

import com.jarvis.user.service.UserSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by jian on 18-3-9
 */
@Component
public class UserSyncJob {

    @Autowired
    private UserSyncService userSyncService;

//    @Scheduled(cron = "0 0 0 * * ?")
    public void doSyncUser() {
        userSyncService.fullImportOldDatasource();
    }
}
