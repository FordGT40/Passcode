package com.wisdom.passcode.base;

import android.content.Context;

import com.wisdom.passcode.ConstantString;
import com.wisdom.passcode.mine.model.PersonalInfoModel;
import com.wisdom.passcode.scanback.model.ScanBackModel;


/**
 * @author HanXueFeng
 * @ProjectName project： Nhoa
 * @class package：
 * @class describe：本地用户偏好管理工具类，存储本地化用户信息
 * @time
 * @change
 */

public class SharedPreferenceUtil {


    public static PrefsConfig getConfig(Context context) {
        return PrefsConfig.getPrefsConfig(new PrefsConfig.Config(
                ConstantString.SHARE_PER_INFO, 0), context);
    }

    /**
     * 获取人员个人信息
     * @param context
     * @param context
     */
    public static PersonalInfoModel getPersonalInfoModel(Context context) {
        return (PersonalInfoModel) getConfig(context).getSerializable(ConstantString.USER_INFO);
    }

    /**
     * 设置人员个人信息
     * @param context
     * @param model
     */
    public static void setPersonalInfoModel(Context context, PersonalInfoModel model) {
        getConfig(context).putSerializable(ConstantString.USER_INFO, model);
    }


    /**
     * 获取人员扫码，填写表单后返回的model
     * @param context
     * @param context
     */
    public static ScanBackModel getScanBackModel(Context context) {
        return (ScanBackModel) getConfig(context).getSerializable(ConstantString.SCAN_INFO);
    }

    /**
     * 存储人员扫码，填写表单后返回的model
     * @param context
     * @param model
     */
    public static void setScanBackModel(Context context, ScanBackModel model) {
        getConfig(context).putSerializable(ConstantString.SCAN_INFO, model);
    }

    /**
     * 取得管理员上次选中的当前场所
     * @param context
     * @return
     */
    public static PersonalInfoModel.PlaceCodeBean getAdminPlaceInfo(Context context) {
        return (PersonalInfoModel.PlaceCodeBean) getConfig(context).getSerializable(ConstantString.ADIN_PLACE_INFO);
    }
    /**
     * 存储管理员上次选中的当前场所
     * @param context
     * @return
     */
    public static void setAdminPlaceInfo(Context context, PersonalInfoModel.PlaceCodeBean model) {
        getConfig(context).putSerializable(ConstantString.ADIN_PLACE_INFO, model);
    }
}
