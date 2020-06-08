package com.wisdom.passcode

/**
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode
 * @class describe：
 * @author HanXueFeng
 * @time 2020/4/22 0022 11:38
 * @change
 */
class ConstantString {
    companion object {

        val RESULT_CODE_CHOOSE_CARD_TYPE: Int = 0x113//申请人员码----选择卡类型后的结果码
        const val RESULT_CODE_CHOOSE_PLACE: Int = 0x111//申请人员码或车辆吗----选择场所后的结果码


        const val CARD_TYPE_PERSON = "1"//申请出入证时候，获取某场所出入证类型可选数据-----人员的
        const val CARD_TYPE_CAR = "2"//申请出入证时候，获取某场所出入证类型可选数据-----车辆的

        const val SCREEN_LIGHT_MAX: Int = 255
        const val SPLASH_WAITING_SECONDS: Long = 4000L//炫耀页面展示的时长
        const val REGISTER_TYPE: String = "1"//发送验证码---注册类型参数
        const val LOGIN_TYPE: String = "2"//发送验证码---登录类型参数
        const val FACE_TYPE: String = "3"//发送验证码---实名认证类型参数
        const val ALTER_PHONE_TYPE: String = "4"//发送验证码---修改手机号
        const val RESET_PSW_TYPE: String = "5"//发送验证码---修改密码类型参数

        const val SHARE_PER_INFO = "passcode_sp"
        const val APP_KEY = "androidkeykeykey"//
        const val APP_SECRET = "androidsecret"
        const val IS_FIRST_LOAD = "firstLoad"
        const val USER_INFO = "user_info"//存储用户模型常量
        const val USER_STATE_TRUE = "1"//用户实名状态：已实名
        const val USER_STATE_FALSE = "0"//用户实名状态：未实名
        const val BROADCAST_FINISH_TAG = "finish"//广播标识，用来关闭某页面
        const val PLACE_TYPE_GOVERNMENT = "1"//申请场所码类型---政府
        const val PLACE_TYPE_COMPANY = "2"//申请场所码类型---企业
        const val PLACE_TYPE_OTHER = "3"//申请场所码类型---其他

        //调取相册相机文件方面的参数常量
        const val REQUEST_CAMERA = 101 //调起相机
        const val ALBUM_SELECT_CODE = 102 //调起相册
        const val FILE_SELECT_CODE = 103 //调起文件选择
        const val REQUEST_CODE = 0x103 //通用的startActivityForResult，请求码

        const val PAPER_TYPE_A = "1"// 1：多证合一营业执照
        const val PAPER_TYPE_B = "2"// 2：普通营业执照
        const val PAPER_TYPE_C = "3"//3：事业单位法人证书或统一社会信用代码证书
        const val PAPER_TYPE_D = "4"// 4：普通组织机构代码证

        const val AUTHENTICATION_TYPE_TRUE = "1"//实名认证状态---已认证
        const val AUTHENTICATION_TYPE_FALSE = "0"//实名认证状态---未认证

        const val RECYCLER_PULL_REFRESH = "refresh"//列表下拉刷新标识
        const val RECYCLER_PULL_LOADMORE = "loadmore"//列表上拉加载标识

        const val AUDITSTATE_CHECK = "1"//我的申请审核状态---待审核
        const val AUDITSTATE_PASS = "2"//我的申请审核状态---已通过
        const val AUDITSTATE_REFUSE = "3"//我的申请审核状态---已驳回

        const val PLACE_QRCODE_HEAD_URL = "http://192.168.1.225:8090/cxm-api/p/qsm/"//扫描场所码，场所码的前缀

        const val SCAN_CODE_TYPE_WALL_CODE = "1"//1：人扫墙
        const val SCAN_CODE_TYPE_ADMIN_SCAN = "2"//2：管理员扫人码
        const val SCAN_CODE_TYPE_ADMIN_SCAN_PASS_LICENCE = "3"//3：管理员扫人通行证
        const val SCAN_CODE_TYPE_ADMIN_SCAN_CAR_PASS = "4"//4：管理员扫车通行证
        const val SCAN_CODE_TYPE_ADMIN_SCAN_PLATE = "5"//5：管理员扫车牌号

        const val SCAN_CODE_TYPE_IN="1"//出入类型  1：入内   2：外出
        const val SCAN_CODE_TYPE_OUT="2"//出入类型  1：入内   2：外出

        const val SCAN_TYPE_SCAN="1"//扫码类型  1：扫码  2：通行证
        const val SCAN_TYPE_LICENCE="2"//扫码类型  1：扫码  2：通行证

        const val PASS_CODE_TYPE_CAR="1"//我的通行证,查询类型----车辆证
        const val PASS_CODE_TYPE_PERSON="2"//我的通行证,查询类型----通行证
        const val PASS_CODE_TYPE_WORK="3"//我的通行证,查询类型----工作证

        const val MY_CODE_PASS_TYPE_NORMAL="2"//通行日期类型1:工作日 2:无限制
        const val MY_CODE_PASS_TYPE_WORKDAYS="1"//通行日期类型 1:工作日 2:无限制

        const val MY_APPLY_CARD_RECORD_UNDERCHECK="1"//我的卡(人员、车辆)申请记录----1.待审核 2.正常使用 3.被拒绝 4.已过期 5.已失效
        const val MY_APPLY_CARD_RECORD_USEING="2"//我的卡(人员、车辆)申请记录----1.待审核 2.正常使用 3.被拒绝 4.已过期 5.已失效
        const val MY_APPLY_CARD_RECORD_REFUSED="3"//我的卡(人员、车辆)申请记录----1.待审核 2.正常使用 3.被拒绝 4.已过期 5.已失效
        const val MY_APPLY_CARD_RECORD_OUT_OFF_DATE="4"//我的卡(人员、车辆)申请记录----1.待审核 2.正常使用 3.被拒绝 4.已过期 5.已失效
        const val MY_APPLY_CARD_RECORD_USELESS="5"//我的卡(人员、车辆)申请记录----1.待审核 2.正常使用 3.被拒绝 4.已过期 5.已失效

        const val PASSCODE_RECORD_TYPE_PERSON="1"//我的通行证申请记录 查询类型 1.车辆证  2.通行证
        const val PASSCODE_RECORD_TYPE_CAR="2"//我的通行证申请记录 查询类型 1.车辆证  2.通行证

        var timeStamp = 0L//时间戳 初始值
        var accessToken = ""//短时间token
        var refreshToken = ""//长效时间戳
        var isAdmin = ""//是否是某场所管理员
        var userIdEncryption = ""//加密过的userId
        var loginState = false//系统的登录状态
        var userPhone = ""//当前登录用户的手机号
        var PIC_LOCATE = "" //拍照后图片缓存的地址


        val LICENCEPAPER =
            "<p class=MsoNormal ><span style='font-family:\"微软雅黑\",\"sans-serif\"'>哈尔滨华泽数码科技有限公司：</span></p>" +

                    "<p class=MsoNormal ><span style='font-family:\"微软雅黑\",\"sans-serif\"'>本企业</span><span" +
                    "lang=EN-US>/</span><span style='font-family:\"微软雅黑\",\"sans-serif\"'>组织</span><span" +
                    "lang=EN-US>_____________________________</span></p>" +

                    "<p class=MsoNormal ><span style='font-family:\"微软雅黑\",\"sans-serif\"'>授权在职员工</span><span" +
                    "lang=EN-US>____________________________</span></p>" +

                    "<p class=MsoNormal ><span style='font-family:\"微软雅黑\",\"sans-serif\"'>（身份证号</span><span" +
                    "lang=EN-US>____________________</span><span style='font-family:\"微软雅黑\",\"sans-serif\"'>）为本企业</span><span" +
                    "lang=EN-US>/</span><span style='font-family:\"微软雅黑\",\"sans-serif\"'>组织管理员。本企业承诺遵守《智通行服务协议》和《智通行公约》，保证内容真实有效并授权企业管理员代表本企业阅读并确认《智通行认证规范》</span></p>" +

                    "<p class=MsoNormal ><span style='font-family:\"微软雅黑\",\"sans-serif\"'>申请企业</span><span" +
                    "lang=EN-US>/</span><span style='font-family:\"微软雅黑\",\"sans-serif\"'>组织（盖章）：</span><span" +
                    "lang=EN-US>___________________</span></p>" +

                    "<p class=MsoNormal ><span style='font-family:\"微软雅黑\",\"sans-serif\"'>授权管理员（签字）</span><span" +
                    "lang=EN-US>_______________________</span></p>" +

                    "<p class=MsoNormal ><span style='font-family:\"微软雅黑\",\"sans-serif\"'>日期</span><span" +
                    "lang=EN-US>___________________</span></p>"
    }

}