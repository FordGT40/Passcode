package com.wisdom.passcode

/**
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode
 * @class describe：
 * @author HanXueFeng
 * @time 2020/5/15 0015 16:29
 * @change
 */
class ConstantUrl {
    companion object {
        const val BASE_URL = "http://192.168.1.225:8090/cxm-api"

        //1. 系统时间戳
        const val SERVER_TIMEMILLIS_URL = "/v1/auth/serverTimeMillis"

        //2. 刷新token
        const val REFRESH_TOKEN_URL = "/v1/auth/refreshToken"

        //发送验证码
        const val SMS_URL = "/v1/user/sms"

        //校验验证码
        const val VERIFY_CODE_URL = "/v1/user/verifyCode"

        //登录
        const val LOGIN_URL = "/v1/user/login"

        //注册
        const val REGISTER_URL = "/v1/user/register"

        //实名认证
        const val AUTHENTICATION_URL = "/v1/user/authentication"

        // 个人信息获取接口
        const val USER_INFO_URL = "/v1/user/info"

        //        地市信息选择接口
        const val SELECT_LOCATION_URL = "/v1/common/region"

        //        申请场所码
        const val APPLY_PLACE_CODE_URL = "/v1/place/apply"

        //我的场所申请列表
        const val APPLY_PLACE_MINE_URL = "/v1/place/mine"

        //10. 根据名称搜索场所列表
        const val PLACE_SEARCH_URL = "/v1/place/search"

        //获取场所通行证类型
        const val CODETYPE_SEARCH_URL = "/v1/codeType/search"

        // 提交场所申请
        const val PASSCODE_APPLY_URL = "/v1/passCode/apply"

        //获取实名认证信息接口（实名认证后在个人中心的实名认证中查看）
        const val USER_AUTHINFO_URL = "/v1/user/authInfo"

        //        通用扫码接口
        const val SCAN_CODE_URL = "/v1/scan/code"

        //        18. 扫码信息记录接口
        const val SCAN_LOG_URL = "/v1/scan/log"

        //更新手机号
        const val UPDATE_PHONE_URL = "/v1/user/updatePhone"

//        16. 我的通行证
        const val PASSCODE_MINE_URL="/v1/passCode/mine"

//        17. 我的通行证申请记录
        const val PASSCODE_APPLYLIST_URL="/v1/passCode/applyList"


    }

}