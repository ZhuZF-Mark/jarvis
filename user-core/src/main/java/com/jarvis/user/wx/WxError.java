package com.jarvis.user.wx;

/**
 * 微信错误返回
 * Created by ZZF on 2018/4/23.
 */
public class WxError {


    private Integer errcode;

    private String errmsg;

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        return "WxError{" +
                "errcode=" + errcode +
                ", errmsg='" + errmsg + '\'' +
                '}';
    }

    //---------- functions

    public boolean valid(){
        return errcode == null || errcode == 0;
    }

}
