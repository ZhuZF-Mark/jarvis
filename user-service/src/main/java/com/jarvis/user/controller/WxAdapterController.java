package com.jarvis.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 微信回调适配器
 * Created by ZZF on 2018/4/25.
 */
@Api(tags = "WX", description = "微信回调适配器")
@RestController
@RequestMapping("/WX")
public class WxAdapterController {

    @RequestMapping(method = RequestMethod.GET, value = "adapter")
    @ApiOperation("适配器")
    public void  adapter(@ApiParam(required = true, name = "code", value = "校验code")@RequestParam("code") String code,
                         @ApiParam(required = true, name = "state", value = "state")@RequestParam("state") String state,
                         @ApiParam(required = true, name = "callbackUrl", value = "回调地址")@RequestParam("callbackUrl") String callbackUrl,
                         HttpServletResponse response) throws IOException {
        response.sendRedirect(callbackUrl+"?code="+code+"&state="+state);
    }
    @RequestMapping(method = RequestMethod.GET, value = "loginSuccess")
    @ApiOperation("适配器")
    public void  loginSuccess(@ApiParam(required = true, name = "code", value = "校验code")@RequestParam("code") String code,
                         @ApiParam( name = "state", value = "state")@RequestParam(value = "state",required = false) String state,
                         @ApiParam(required = true, name = "callbackUrl", value = "回调地址")@RequestParam("callbackUrl") String callbackUrl,
                         HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        StringBuffer sb=new StringBuffer();
        sb.append("<html>");
        sb.append("<body>");
        sb.append("<input type=\"text\" id=\"clock\" />\n");
        sb.append("</br>");
        sb.append("<script type=\"text/javascript\">\n");
        sb.append("var int=self.setInterval(\"clock()\",1000);\n"+"var d=5;\n");
        sb.append("function clock()\n" +
                "{\n" +
                "if(d==0){ window.clearInterval(int);}"+
                "var t=d--;\n" +
                "document.getElementById(\"clock\").value=t;\n" +
                "}\n" +
                "</script>\n");

        sb.append("<img src='http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83erxuBhBR3hF7NQsDbjF6Zqqfj2oiaLpzOSFm2Micib39zLdYh88FB13UuA4YVLruSibkvibvt8DyjocNgg/132' width='350' height='370'>");
        sb.append("</body></html>");

        OutputStream out = response.getOutputStream();
        out.write(sb.toString().getBytes());
        out.flush();
        out.close();
    }
    @RequestMapping(method = RequestMethod.GET, value = "loginSuccess1")
    @ApiOperation("适配器")
    public void  loginSuccess1(@ApiParam(required = true, name = "code", value = "校验code")@RequestParam("code") String code,
                              @ApiParam( name = "state", value = "state")@RequestParam(value = "state",required = false) String state,
                              @ApiParam(required = true, name = "callbackUrl", value = "回调地址")@RequestParam("callbackUrl") String callbackUrl,
                              HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        //从文件地址中读取内容到程序中
        //1、建立连接
        BufferedReader bis = new BufferedReader(new InputStreamReader(new FileInputStream("user-service/src/main/javascript/timeout.html")));
        //2、开始读取信息
        //先定义一个字节数组存放数据
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = bis.readLine()) != null) {
            if(line.indexOf("CALLBACKURL")!=-1){
            line= line.replaceAll("CALLBACKURL",callbackUrl);
            }
            sb.append(line + "\n");
        }

        OutputStream out = response.getOutputStream();
        out.write(sb.toString().getBytes());
        out.flush();
        out.close();

    }
}
