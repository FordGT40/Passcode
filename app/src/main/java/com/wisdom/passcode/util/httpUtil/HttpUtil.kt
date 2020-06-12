package com.wisdom.passcode.util.httpUtil

import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.HttpParams
import com.wisdom.passcode.AppApplication
import com.wisdom.passcode.ConstantString
import com.wisdom.passcode.ConstantUrl
import com.wisdom.passcode.base.SharedPreferenceUtil
import com.wisdom.passcode.util.EncrypAndDecrypUtil
import com.wisdom.passcode.util.LogUtil
import com.wisdom.passcode.util.MD5Util
import com.wisdom.passcode.util.httpUtil.callback.BaseModel
import java.util.*
import com.wisdom.passcode.util.httpUtil.callback.JsonCallback
import com.wisdom.passcode.util.httpUtil.callback.StringsCallback

/**
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.util.httpUtil
 * @class describe：
 * @author HanXueFeng
 * @time 2020/5/15 0015 16:34
 * @change
 */
class HttpUtil {
    companion object {
        fun httpPost(
            url: String?,
            params: HttpParams?,
            callback: StringCallback
        ) {
            OkGo.post(getAbsolteUrl(url))
                .cacheMode(CacheMode.DEFAULT)
                .params(params)
                .headers("appkey", ConstantString.APP_KEY)
                .headers("Content-Type", "multipart/form-data")
                .execute(callback)
        }


        fun httpPostWithStampAndSign(
            url: String?,
            params: HttpParams,
            paramsList: MutableList<String>,
            callback: StringCallback
        ) {
            //将时间戳添加到待加密数组中
            val timeStamp = (Date().time + ConstantString.timeStamp)
            paramsList.add("timestamp${timeStamp}")
            params.put("timestamp", timeStamp)
            params.put("sign", getInterfaceSign(paramsList))
            OkGo.post(getAbsolteUrl(url))
                .cacheMode(CacheMode.DEFAULT)
                .params(params)
                .headers("appkey", ConstantString.APP_KEY)
                .headers("Content-Type", "multipart/form-data")
                .execute(callback)
        }




        fun httpPostWithStampAndSignToken(
            url: String?,
            params: HttpParams,
            paramsList: MutableList<String>,
            callback: StringCallback
        ) {
            //将时间戳添加到待加密数组中
            val timeStamp = (Date().time + ConstantString.timeStamp)
            paramsList.add("timestamp${timeStamp}")
            params.put("timestamp", timeStamp)
            val token =ConstantString.accessToken
            params.put("sign", getInterfaceSign(paramsList))
            OkGo.post(getAbsolteUrl(url))
                .cacheMode(CacheMode.DEFAULT)
                .params(params)
                .headers("accesstoken", token)
                .headers("appkey", ConstantString.APP_KEY)
                .headers("Content-Type", "multipart/form-data")
                .execute(callback)
        }

        /**
         *  @describe 带有自动刷新Token操作的网络访问工具类，区别在于传入的是StringsCallBack
         *  @return 
         *  @author HanXueFeng
         *  @time 2020/5/21 0021  14:10
         */
        fun httpPostWithStampAndSignToken(
            url: String?,
            params: HttpParams,
            paramsList: MutableList<String>,
            callback: StringsCallback
        ) {
            //将时间戳添加到待加密数组中
            val timeStamp = (Date().time + ConstantString.timeStamp)
            paramsList.add("timestamp${timeStamp}")
            params.put("timestamp",timeStamp)
            val token =ConstantString.accessToken
            params.put("sign", getInterfaceSign(paramsList))
            OkGo.post(getAbsolteUrl(url))
                .cacheMode(CacheMode.DEFAULT)
                .params(params)
                .headers("accesstoken", token)
                .headers("appkey", ConstantString.APP_KEY)
                .headers("Content-Type", "multipart/form-data")
                .execute(callback)
        }
        
        

        /**
         *  @describe 更新本地token用的专用接口方法
         *  @return 
         *  @author HanXueFeng
         *  @time 2020/5/21 0021  14:03
         */
        fun httpUpdateToken(
            callback: StringCallback
        ) {
            //将时间戳添加到待加密数组中
            val params=HttpParams()
            val timeStamp = (Date().time + ConstantString.timeStamp)
            val refreshToken =ConstantString.refreshToken
            val paramsList= listOf("timestamp${timeStamp}").toMutableList()
            params.put("timestamp", timeStamp)
            params.put("sign", getInterfaceSign(paramsList))
            OkGo.post(getAbsolteUrl(ConstantUrl.REFRESH_TOKEN_URL))
                .cacheMode(CacheMode.DEFAULT)
                .params(params)
                .headers("refreshtoken", refreshToken)
                .headers("appkey", ConstantString.APP_KEY)
                .headers("Content-Type", "multipart/form-data")
                .execute(callback)

        }




//---------------------------------------------------以下是有关网络访问的辅助工具方法（包括但不限于加密等）------------------------------------
        /**
         *  @describe 根据错误码获得错误信息
         *  @return
         *  @author HanXueFeng
         *  @time 2020/5/15 0015  16:53
         */
        fun getErrorMsgByCode(errorCode: String): String {
            return if (errorMsgMap.containsKey(errorCode)) {
                errorMsgMap.getValue(errorCode)
            } else {
                "服务器错误"
            }
        }

        //        异常代码维护
        private val errorMsgMap = mapOf(
            Pair("0", "成功")
            , Pair("101", "服务端异常")
            , Pair("103", "请求参数缺失")
            , Pair("104", "请求参数不规范")
            , Pair("201", "手机号已被使用")
            , Pair("202", "验证码错误")
            , Pair("203", "账号不存在")
            , Pair("204", "密码错误")
            , Pair("205", "两次密码不相同")
            , Pair("206", "验证码发送频繁（1分钟一次）")
            , Pair("207", "账号被封禁")
            , Pair("301", "签名错误")
            , Pair("302", "AppKey错误")
            , Pair("303", "签名时间戳错误（data返回系统时间戳）")
            , Pair("401", "权限不足（token异常）")
            , Pair("402", "重复请求（相同请求参数，短时间内重复提交 5S）")
            , Pair("403", "刷新token无效")
            , Pair("501", "登陆失效，请重新登录")
            , Pair("210", "身份证照片匹配失败")
            , Pair("211", "统一信用代码已存在")
            , Pair("999", "全局异常")
            , Pair("", "")

        )

        /**
         * 拼接并返回完整的请求地址
         */
         fun getAbsolteUrl(relativeUrl: String?): String? {
            return ConstantUrl.BASE_URL.toString() + relativeUrl
        }

        /**
         *  @describe 根据参数key value 得到该接口的签名串
         *  1. 签名算法
         * 假设请求参数
         * api=ykse.partner.seat.getSeats&v=1.0&channelCode=YKSE&timestamp=1455678273752
         * 首先按照参数字母排序相加参数值得到
         * apiykse.partner.seat.getSeatschannelCodeYKSEtimestamp1455678273752v1.0
         * 再加上 appkey 对应的密钥，假设为 12345，得到
         * apiykse.partner.seat.getSeatschannelCodeYKSEtimestamp1455678273752v1.012345
         * 使用 md5 进行 hash，得到 16 进制的签名
         *  1dac7e94adf6fe5a28621c0ddba27ea5
         *  @return
         *  @author HanXueFeng
         *  @time 2020/5/15 0015  17:35
         */
         fun getInterfaceSign(list: MutableList<String>): String {
            list.sort()
            var keyValueStr =""
            for (item in list) {
                keyValueStr += item
                LogUtil.getInstance().d(item)
            }
            LogUtil.getInstance().e("签名之前的串：${keyValueStr + ConstantString.APP_SECRET}")
            LogUtil.getInstance().e("签名之后的串：${MD5Util.MD5(keyValueStr + ConstantString.APP_SECRET)}")
            return MD5Util.MD5(keyValueStr + ConstantString.APP_SECRET)

        }

    }
}