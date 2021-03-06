package com.wisdom.passcode.util.httpUtil.callback;

import android.content.Intent;
import android.content.SharedPreferences;

import com.lzy.okgo.callback.StringCallback;
import com.wisdom.passcode.AppApplication;
import com.wisdom.passcode.ConstantString;
import com.wisdom.passcode.R;
import com.wisdom.passcode.base.ActivityManager;
import com.wisdom.passcode.base.SharedPreferenceUtil;
import com.wisdom.passcode.mine.activity.LoginActivity;
import com.wisdom.passcode.util.LogUtil;
import com.wisdom.passcode.util.ToastUtil;
import com.wisdom.passcode.util.Tools;
import com.wisdom.passcode.util.httpUtil.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @authorzhanglichao
 * @date2018/11/30 14:42
 * @package_name com.wisdom.hrbzwt.util.http_util.callback
 */
public abstract class StringsCallback extends StringCallback {
    private OnTokenRefreshSuccessListener listener;

    public StringsCallback(OnTokenRefreshSuccessListener listener) {
        this.listener = listener;
    }


    @Override
    public void onSuccess(String s, Call call, Response response) {
//
        try {
            JSONObject jsonObject = new JSONObject(s);
            int code = jsonObject.optInt("code");
            if (code == 401) {
                //token过期。需要刷新token,并通过接口将结果回调给业务处理部分
                HttpUtil.Companion.httpUpdateToken(new StringCallback() {
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (listener != null) {
                            listener.onRefreshFail(e.getMessage());
                            Tools.Companion.closeDialog();
                        }
                    }

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            Tools.Companion.closeDialog();
                            JSONObject jsonObject1 = new JSONObject(s);
                            int code = jsonObject1.optInt("code");
                            if (code == 0) {
                                //刷新Token成功，解析返回数据中的token
                                JSONObject jsonObject2 = jsonObject1.optJSONObject("data");
                                String accessToken = jsonObject2.optString("accessToken");
                                String refreshToken = jsonObject2.optString("refreshToken");
                                //将解析的token刷写到全局变量以及sp文件中
                                ConstantString.Companion.setAccessToken(accessToken);
                                ConstantString.Companion.setRefreshToken(refreshToken);
                                SharedPreferences.Editor editor = SharedPreferenceUtil.getConfig(AppApplication.Companion.getInstance()).getSharedPreferences().edit();
                                editor.putString("accessToken", accessToken);
                                editor.putString("refreshToken", refreshToken);
                                editor.apply();
                                Tools.Companion.closeDialog();
                                listener.onRefreshSuccess();
                                //
                            } else if (code == 403) {
                                //刷新用的token也失效了，所以跳转登录页
                                if (listener != null) {
                                    Tools.Companion.closeDialog();
                                    listener.onRefreshFail(HttpUtil.Companion.getErrorMsgByCode(code + ""));
                                }
                                SharedPreferenceUtil.getConfig(AppApplication.Companion.getInstance()).clearSharePrefernence();
                                //本地关键信息置空
                                ConstantString.Companion.setAccessToken("");
                                ConstantString.Companion.setRefreshToken("");
                                ConstantString.Companion.setTimeStamp(0L);
                                ConstantString.Companion.setUserPhone("");
                                ConstantString.Companion.setLoginState(false);
                                ConstantString.Companion.setAdmin("");
                                ConstantString.Companion.setUserIdEncryption("");

                                ToastUtil.Companion.showToast(R.string.login_time_out);
                                Intent intent = new Intent(AppApplication.Companion.getInstance(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                AppApplication.Companion.getInstance().startActivity(intent);
                                ActivityManager.getActivityManagerInstance().clearAllActivity();
                            } else {
                                //刷新Token失败，将失败原因回调到业务处理部分
                                if (listener != null) {
                                    Tools.Companion.closeDialog();
                                    listener.onRefreshFail(HttpUtil.Companion.getErrorMsgByCode(code + ""));
                                    LogUtil.getInstance().e(HttpUtil.Companion.getErrorMsgByCode(code + ""));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            } else {
                //token没有过期
Tools.Companion.closeDialog();
                onInterfaceSuccess(jsonObject, call, response);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        super.onError(call, response, e);

    }

    public abstract void onInterfaceSuccess(JSONObject jsonObject, Call call, Response response);


    public interface OnTokenRefreshSuccessListener {
        void onRefreshSuccess();

        void onRefreshFail(String msg);
    }


}
