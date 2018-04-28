//引入微信js
document.write("<script type='text/javascript' src='http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js'/>");

    /**
     微信二维码展示
     id：页面二维码区域id
     callbackUrl:登录成功后跳转的地址
     width:展示区域宽度
     height:展示区域高度
     */
    function getWXcode(id,callbackUrl,width,height){
        var redirectUri="https%3A%2F%2Fauth.jwsmed.com?"+callbackUrl+"&"+width+"&"+height;
        var obj = new WxLogin({
            self_redirect:false,
            id:id,
            appid: "wxdf06384c690fe4f2",
            scope: "snsapi_login",
            redirect_uri: redirectUri,
            state: "3d6be0a4035d839573b04816624a415e",
            style: "blank"
        });
    }

/**
 登录请求，发送用户登录信息
 返回promise
 */
function login(url,userData) {
    return new Promise(function(resolve, reject) {
        var XHR = new XMLHttpRequest();
        XHR.open('POST', url, true);
        XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        XHR.send(userData);
        XHR.onreadystatechange = function() {
            if (XHR.readyState == 4) {
                if (XHR.status == 200) {
                    try {
                        var response = JSON.parse(XHR.responseText);
                        resolve(response);
                    } catch (e) {
                        reject(e);
                    }
                } else {
                    reject(new Error(XHR.statusText));
                }
            }
        }
    })
}
