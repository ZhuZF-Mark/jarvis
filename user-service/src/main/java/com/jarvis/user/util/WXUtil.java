package com.jarvis.user.util;

import com.jarvis.user.wx.WxAccessToken;
import com.jarvis.user.wx.WxBaseUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信请求工具类
 * Created by ZZF on 2018/4/24.
 */
@Component
public class WXUtil {
    @Autowired
    private  RestTemplate restTemplate;
    //微信密钥
    public static  final String  AppSecret="ae130133656a5d00b9b4bc0fdb168fbf";
    //微信id
    public static  final String  Appid="wxdf06384c690fe4f2";
    /**
     * 微信请求access_token
     */
    public  WxAccessToken getAccessToken(String code){
        String url="https://api.weixin.qq.com/sns/oauth2/access_token?"+"appid={appid}&secret={secret}&code={code}&grant_type={grant_type}";
        Map<String, String> postData = new HashMap<>();
        postData.put("appid", Appid);
        postData.put("secret", AppSecret);
        postData.put("code", code);
        postData.put("grant_type", "authorization_code");
        WxAccessToken accessToken = restTemplate.getForObject(url, WxAccessToken.class, postData);
        return  accessToken;
    }

    /**
     * 微信请求用户信息
     * @param accessToken
     * @param openId
     * @return
     */
    public  WxBaseUserInfo getUserInfo(String accessToken,String openId){
        String url="https://api.weixin.qq.com/sns/userinfo?"+"access_token={access_token}&openid={openid}";
        Map<String, String> postData = new HashMap<>();
        postData.put("access_token", accessToken);
        postData.put("openid", openId);
        WxBaseUserInfo userInfo = restTemplate.getForObject(url, WxBaseUserInfo.class, postData);
        return  userInfo;
    }
}
