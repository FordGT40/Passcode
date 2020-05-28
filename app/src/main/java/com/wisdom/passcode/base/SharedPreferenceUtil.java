package com.wisdom.passcode.base;

import android.content.Context;

import com.wisdom.passcode.ConstantString;
import com.wisdom.passcode.mine.model.PersonalInfoModel;


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


    public static PersonalInfoModel getPersonalInfoModel(Context context) {
        return (PersonalInfoModel) getConfig(context).getSerializable(ConstantString.USER_INFO);
    }

    public static void setPersonalInfoModel(Context context, PersonalInfoModel model) {
        getConfig(context).putSerializable(ConstantString.USER_INFO, model);
    }
}
