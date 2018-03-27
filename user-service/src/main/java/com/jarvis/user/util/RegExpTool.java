/**
 * hongshiumei.com Inc.
 * Copyright (c) 2016-2017 All Rights Reserved.
 */
/**
 * @Title: RegExpTool.java
 * @Package com.runlion.website.common.utils
 * Copyright: Copyright (c) 2016 
 * Company:红狮优煤
 * 
 * @author zhuzf
 * @date 2017年11月20日 下午5:37:55
 * @version V1.0
 */
package com.jarvis.user.util;

import java.util.regex.Pattern;

/**
  *  RegExpTool
  *  正则表达式
  * @author zhuzf
  *  2017年11月20日 下午5:37:55
  * @version $Id: RegExpTool.java, v 0.1 2017年11月20日 下午5:37:55 Mark Exp $
  */
public class RegExpTool {
    private static String pwdReg    = "^[/!/@/#/$/%/_A-Za-z0-9]{6,16}$";

    private static String mobileReg = "^1[0-9]{10}$";

    private static String emailReg  = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 密码是否满足规则的
     * 
     * @param txt
     * @return String
     */
    public static boolean pwdMatch(String txt) {
        return Pattern.matches(pwdReg, txt);
    }

    /**
     * 手机号是否满足
     * 
     * @param txt
     * @return boolean
     */
    public static boolean mobileMatch(String txt) {
        return Pattern.matches(mobileReg, txt);
    }

    /**
     * 
     * 邮箱是否满足
     * @param txt
     * @return boolean
     */
    public static boolean emailMatch(String txt) {
        return Pattern.matches(emailReg, txt);
    }
}
