package com.jarvis.user.util;

import java.util.UUID;

/**
 * Created by ZZF on 2018/3/12.
 */
public class CodeUtil {
    public  String getUuidCode(){
        //生成唯一code返回给前端
        String code= String.valueOf(UUID.randomUUID());
        return code;
    }
}
