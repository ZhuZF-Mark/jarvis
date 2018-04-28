package com.jarvis.user.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回包装类
 * Created by ZZF on 2018/4/24.
 */
public class ResultMap {
    //默认false
    private static  Boolean success=false;
    //返回信息
    private static String msg;
    //body
    private static Object body;

    /**
     * 返回自定义
     * @param success 是否成功
     * @param body 内容
     * @param msg 消息
     */
    public static Map<String,Object> result(Boolean success,Object body,String msg,String code){
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("success",success);
        resultMap.put("body",body);
        resultMap.put("msg",msg);
        return resultMap;
    }

    /**
     * 返回成功
     * @param body 内容
     */
    public static Map<String,Object> successResult(Object body){
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("success",true);
        resultMap.put("body",body);
        return resultMap;
    }
    /**
     * 返回失败
     * @param body 内容
     */
    public static Map<String,Object> failResult(Object body,String msg,String code){
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("success",false);
        resultMap.put("body",body);
        resultMap.put("code",code);
        resultMap.put("msg",msg);
        return resultMap;
    }
}
