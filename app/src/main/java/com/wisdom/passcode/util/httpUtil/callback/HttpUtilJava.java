package com.wisdom.passcode.util.httpUtil.callback;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;
import com.wisdom.passcode.AppApplication;
import com.wisdom.passcode.ConstantString;
import com.wisdom.passcode.base.SharedPreferenceUtil;
import com.wisdom.passcode.util.httpUtil.HttpUtil;

import java.util.Date;
import java.util.List;

/**
 * @author HanXueFeng
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.util.httpUtil.callback
 * @class describe：
 * @time 2020/5/20 0020 15:14
 * @change
 */
public class HttpUtilJava {
    public  static void httpPostWithStampAndSignToken(
            String url,
            HttpParams params,
            List<String> paramsList,
            JsonCallback callback
    ) {
        //将时间戳添加到待加密数组中
        long timeStamp = (new Date().getTime() + ConstantString.Companion.getTimeStamp());
        paramsList.add("timestamp" + timeStamp);
        params.put("timestamp", timeStamp);
        String token =
                SharedPreferenceUtil.getConfig(AppApplication.Companion.getInstance()).getSharedPreferences().getString(
                        "accesstoken",
                        ""
                );
        params.put("sign", HttpUtil.Companion.getInterfaceSign(paramsList));
        OkGo.post(HttpUtil.Companion.getAbsolteUrl(url))
                .cacheMode(CacheMode.DEFAULT)
                .params(params)
                .headers("accesstoken", token)
                .headers("appkey", ConstantString.APP_KEY)
                .headers("Content-Type", "multipart/form-data")
                .execute(callback);
    }
}
