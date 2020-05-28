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

    }

}