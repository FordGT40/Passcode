package com.wisdom.passcode.push;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.android.pushservice.PushMessageReceiver;
import com.google.gson.Gson;
import com.wisdom.passcode.base.SharedPreferenceUtil;
import com.wisdom.passcode.helper.Helper;
import com.wisdom.passcode.scanback.activity.AgreeAccessActivity;
import com.wisdom.passcode.scanback.activity.CodeResultActivity;
import com.wisdom.passcode.scanback.model.ScanBackModel;
import com.wisdom.passcode.util.AlertUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/*
 * Push��Ϣ����receiver�����д����Ҫ�Ļص������� һ����˵�� onBind�Ǳ���ģ���������startWork����ֵ��
 * onMessage��������͸����Ϣ�� onSetTags��onDelTags��onListTags��tag��ز����Ļص���
 * onNotificationClicked��֪ͨ�����ʱ�ص��� onUnbind��stopWork�ӿڵķ���ֵ�ص�
 * ����ֵ�е�errorCode���������£�
 * 0 - Success
 * 10001 - Network Problem
 * 10101 - Integrate Check Error
 * 30600 - Internal Server Error
 * 30601 - Method Not Allowed
 * 30602 - Request Params Not Valid
 * 30603 - Authentication Failed
 * 30604 - Quota Use Up Payment Required
 * 30605 - Data Required Not Found
 * 30606 - Request Time Expires Timeout
 * 30607 - Channel Token Timeout
 * 30608 - Bind Relation Not Found
 * 30609 - Bind Number Too Many
 * �����������Ϸ��ش���ʱ��������Ͳ����������⣬����ͬһ����ķ���ֵrequestId��errorCode��ϵ����׷�����⡣
 *
 */


public class MyPushMessageReceiver extends PushMessageReceiver {
    /**
     * TAG to Log
     */
    public static final String TAG = MyPushMessageReceiver.class
            .getSimpleName();

    /**
     * ����PushManager.startWork��sdk����push
     * server�������������������첽�ġ�������Ľ��ͨ��onBind���ء� �������Ҫ�õ������ͣ���Ҫ�������ȡ��channel
     * id��user id�ϴ���Ӧ��server�У��ٵ���server�ӿ���channel id��user id�������ֻ������û����͡�
     *
     * @param context   BroadcastReceiver��ִ��Context
     * @param errorCode �󶨽ӿڷ���ֵ��0 - �ɹ�
     * @param appid     Ӧ��id��errorCode��0ʱΪnull
     * @param userId    Ӧ��user id��errorCode��0ʱΪnull
     * @param channelId Ӧ��channel id��errorCode��0ʱΪnull
     * @param requestId �����˷��������id����׷������ʱ���ã�
     * @return none
     */
    @Override
    public void onBind(Context context, int errorCode, String appid,
                       String userId, String channelId, String requestId) {
        String responseString = "onBind errorCode=" + errorCode + " appid="
                + appid + " userId=" + userId + " channelId=" + channelId
                + " requestId=" + requestId;
        Log.d(TAG, responseString);

        if (errorCode == 0) {
            Helper.Companion.setChannelId(channelId);
            // �󶨳ɹ�
            Log.d(TAG, "�󶨳ɹ�");
        }
        // Demo���½���չʾ���룬Ӧ��������������Լ��Ĵ����߼�

    }

    /**
     * ����͸����Ϣ�ĺ�����
     *
     * @param context             ������
     * @param message             ���͵���Ϣ
     * @param customContentString �Զ�������,Ϊ�ջ���json�ַ���
     */
    @Override
    public void onMessage(Context context, String message,
                          String customContentString) {
        String messageString = "͸����Ϣ onMessage=\"" + message
                + "\" customContentString=" + customContentString;
        Log.d(TAG, messageString);

        // �Զ������ݻ�ȡ��ʽ��mykey��myvalue��Ӧ͸����Ϣ����ʱ�Զ������������õļ���ֵ
        if (!TextUtils.isEmpty(customContentString)) {
            JSONObject customJson = null;
            try {
                customJson = new JSONObject(customContentString);
                String myvalue = null;
                if (!customJson.isNull("mykey")) {
                    myvalue = customJson.getString("mykey");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Demo���½���չʾ���룬Ӧ��������������Լ��Ĵ����߼�

    }

    /**
     * ����֪ͨ����ĺ�����
     *
     * @param context             ������
     * @param title               ���͵�֪ͨ�ı���
     * @param description         ���͵�֪ͨ������
     * @param customContentString �Զ������ݣ�Ϊ�ջ���json�ַ���
     */

    @Override
    public void onNotificationArrived(Context context, String title,
                                      String description, String customContentString) {

        String notifyString = "֪ͨ���� onNotificationArrived  title=\"" + title
                + "\" description=\"" + description + "\" customContent="
                + customContentString;
        Log.d(TAG, notifyString);

        // �Զ������ݻ�ȡ��ʽ��mykey��myvalue��Ӧ֪ͨ����ʱ�Զ������������õļ���ֵ
        if (!TextUtils.isEmpty(customContentString)) {
            JSONObject customJson = null;
            try {
                customJson = new JSONObject(customContentString);
                String myvalue = null;
                if (!customJson.isNull("mykey")) {
                    myvalue = customJson.getString("mykey");
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    }

    /**
     * ����֪ͨ����ĺ�����
     *
     * @param context             ������
     * @param title               ���͵�֪ͨ�ı���
     * @param description         ���͵�֪ͨ������
     * @param customContentString �Զ������ݣ�Ϊ�ջ���json�ַ���
     */
    @Override
    public void onNotificationClicked(Context context, String title,
                                      String description, String customContentString) {
        String notifyString = "֪ͨ��� onNotificationClicked title=\"" + title + "\" description=\""
                + description + "\" customContent=" + customContentString;
        Log.d(TAG, notifyString);
        // 返回后自定义信息解析
        if (!TextUtils.isEmpty(customContentString)) {
            ScanBackModel.PushDataBean pushBean = new Gson().fromJson(customContentString, ScanBackModel.PushDataBean.class);
            Intent intentNext = new Intent();
            if ("1".equals(pushBean.getPushType())) {
//               1推送给被拜访人
                intentNext.setClass(context, AgreeAccessActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", pushBean);
                intentNext.putExtras(bundle);
                context.startActivity(intentNext);
            } else if ("2".equals(pushBean.getPushType())) {
//       2.拜访人点击同意否，推送给扫码用户
                if ("1".equals(pushBean.getAgree())) {
                    ScanBackModel model = SharedPreferenceUtil.getScanBackModel(context);
                    intentNext.setClass(context, CodeResultActivity.class);
                    intentNext.putExtra("auth", model.getAuth());
                    intentNext.putExtra("black", model.getBlack());
                    intentNext.putExtra("agree", model.getAgree());
                    intentNext.putExtra("placeName", pushBean.getPlaceCodeName());
                    intentNext.putExtra("type", pushBean.getType());
                    context.startActivity(intentNext);
                } else {
                    AlertUtil.showMsgAlert(context, "您已被拒绝进入");
                }
            } else if ("3".equals(pushBean.getAgree())) {
//               3. 拜访人点击同意否，推送给场所码相关人员(门卫处)
                AlertUtil.showMsgAlert(context, "门卫收到消息");

            } else {

            }
        }


    }


    @Override
    public void onSetTags(Context context, int errorCode,
                          List<String> successTags, List<String> failTags, String requestId) {
        String responseString = "onSetTags errorCode=" + errorCode
                + " successTags=" + successTags + " failTags=" + failTags
                + " requestId=" + requestId;
        Log.d(TAG, responseString);


    }

    /**
     * delTags() �Ļص�������
     * @param context     ������
     * @param errorCode   �����롣0��ʾĳЩtag�Ѿ�ɾ���ɹ�����0��ʾ����tag��ɾ��ʧ�ܡ�
     * @param successTags �ɹ�ɾ����tag
     * @param failTags    ɾ��ʧ�ܵ�tag
     * @param requestId   ������������͵������id
     */
    @Override
    public void onDelTags(Context context, int errorCode,
                          List<String> successTags, List<String> failTags, String requestId) {
        String responseString = "onDelTags errorCode=" + errorCode
                + " successTags=" + successTags + " failTags=" + failTags
                + " requestId=" + requestId;
        Log.d(TAG, responseString);

    }

    /**
     * listTags() �Ļص�������
     *
     * @param context   ������
     * @param errorCode �����롣0��ʾ�о�tag�ɹ�����0��ʾʧ�ܡ�
     * @param tags      ��ǰӦ�����õ�����tag��
     * @param requestId ������������͵������id
     */
    @Override
    public void onListTags(Context context, int errorCode, List<String> tags,
                           String requestId) {
        String responseString = "onListTags errorCode=" + errorCode + " tags="
                + tags;
        Log.d(TAG, responseString);

        // Demo���½���չʾ���룬Ӧ��������������Լ��Ĵ����߼�

    }

    /**
     * PushManager.stopWork() �Ļص�������
     *
     * @param context   ������
     * @param errorCode �����롣0��ʾ�������ͽ�󶨳ɹ�����0��ʾʧ�ܡ�
     * @param requestId ������������͵������id
     */
    @Override
    public void onUnbind(Context context, int errorCode, String requestId) {
        String responseString = "onUnbind errorCode=" + errorCode
                + " requestId = " + requestId;
        Log.d(TAG, responseString);

        if (errorCode == 0) {
            // ��󶨳ɹ�
            Log.d(TAG, "���ɹ�");
        }
        // Demo���½���չʾ���룬Ӧ��������������Լ��Ĵ����߼�

    }


}
