package com.wisdom.passcode.push

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.baidu.android.pushservice.PushMessageReceiver
import com.google.gson.Gson
import com.wisdom.passcode.helper.Helper.Companion.setChannelId
import com.wisdom.passcode.scanback.activity.AgreeAccessActivity
import com.wisdom.passcode.scanback.activity.GuardResultActivity
import com.wisdom.passcode.scanback.activity.VisitorResultActivity
import com.wisdom.passcode.scanback.model.ScanBackModel.PushDataBean
import com.wisdom.passcode.util.AlertUtil
import org.json.JSONException
import org.json.JSONObject

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
class MyPushMessageReceiver : PushMessageReceiver() {
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
    override fun onBind(
        context: Context, errorCode: Int, appid: String,
        userId: String, channelId: String, requestId: String
    ) {
        val responseString = ("onBind errorCode=" + errorCode + " appid="
                + appid + " userId=" + userId + " channelId=" + channelId
                + " requestId=" + requestId)
        Log.d(TAG, responseString)
        if (errorCode == 0) {
            setChannelId(channelId)
            // �󶨳ɹ�
            Log.d(TAG, "�󶨳ɹ�")
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
    override fun onMessage(
        context: Context, message: String,
        customContentString: String
    ) {
        val messageString = ("͸����Ϣ onMessage=\"" + message
                + "\" customContentString=" + customContentString)
        Log.d(TAG, messageString)

        // �Զ������ݻ�ȡ��ʽ��mykey��myvalue��Ӧ͸����Ϣ����ʱ�Զ������������õļ���ֵ
        if (!TextUtils.isEmpty(customContentString)) {
            var customJson: JSONObject? = null
            try {
                customJson = JSONObject(customContentString)
                var myvalue: String? = null
                if (!customJson.isNull("mykey")) {
                    myvalue = customJson.getString("mykey")
                }
            } catch (e: JSONException) {
                e.printStackTrace()
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
    override fun onNotificationArrived(
        context: Context, title: String,
        description: String, customContentString: String
    ) {
        val notifyString = ("֪ͨ���� onNotificationArrived  title=\"" + title
                + "\" description=\"" + description + "\" customContent="
                + customContentString)
        Log.d(TAG, notifyString)

        // �Զ������ݻ�ȡ��ʽ��mykey��myvalue��Ӧ֪ͨ����ʱ�Զ������������õļ���ֵ
        if (!TextUtils.isEmpty(customContentString)) {
            var customJson: JSONObject? = null
            try {
                customJson = JSONObject(customContentString)
                var myvalue: String? = null
                if (!customJson.isNull("mykey")) {
                    myvalue = customJson.getString("mykey")
                }
            } catch (e: JSONException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
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
    override fun onNotificationClicked(
        context: Context, title: String,
        description: String, customContentString: String
    ) {
        val notifyString =
            ("֪ͨ��� onNotificationClicked title=\"" + title + "\" description=\""
                    + description + "\" customContent=" + customContentString)
        Log.d(TAG, notifyString)
        // 返回后自定义信息解析
        if (!TextUtils.isEmpty(customContentString)) {
            val pushBean =
                Gson().fromJson(customContentString, PushDataBean::class.java)
            val intentNext = Intent()
            when {
                "1" == pushBean.pushType -> {
        //               1推送给被拜访人
                    intentNext.setClass(context, AgreeAccessActivity::class.java)
                    val bundle = Bundle()
                    bundle.putSerializable("data", pushBean)
                    intentNext.putExtras(bundle)
                    context.startActivity(intentNext)
                }
                "2" == pushBean.pushType -> {
        //       2.拜访人点击同意否，推送给扫码用户
                    val isAgree= "1" == pushBean.agree
                    val intent = Intent(context, VisitorResultActivity::class.java)
                    val bundle = Bundle()
                    bundle.putSerializable("data", pushBean)
                    intent.putExtras(bundle)
                    intent.putExtra("isAgree", isAgree)
                    context.startActivity(intent)
                }
                "3" == pushBean.agree -> {
        //               3. 拜访人点击同意否，推送给场所码相关人员(门卫处)
                    val isAgree= "1" == pushBean.agree
                    val intent = Intent(context, GuardResultActivity::class.java)
                    val bundle = Bundle()
                    bundle.putSerializable("data", pushBean)
                    intent.putExtras(bundle)
                    intent.putExtra("isAgree", isAgree)
                    context.startActivity(intent)
                }
                else -> {
                }
            }
        }
    }

    override fun onSetTags(
        context: Context,
        errorCode: Int,
        successTags: List<String>,
        failTags: List<String>,
        requestId: String
    ) {
        val responseString = ("onSetTags errorCode=" + errorCode
                + " successTags=" + successTags + " failTags=" + failTags
                + " requestId=" + requestId)
        Log.d(TAG, responseString)
    }

    /**
     * delTags() �Ļص�������
     * @param context     ������
     * @param errorCode   �����롣0��ʾĳЩtag�Ѿ�ɾ���ɹ�����0��ʾ����tag��ɾ��ʧ�ܡ�
     * @param successTags �ɹ�ɾ����tag
     * @param failTags    ɾ��ʧ�ܵ�tag
     * @param requestId   ������������͵������id
     */
    override fun onDelTags(
        context: Context,
        errorCode: Int,
        successTags: List<String>,
        failTags: List<String>,
        requestId: String
    ) {
        val responseString = ("onDelTags errorCode=" + errorCode
                + " successTags=" + successTags + " failTags=" + failTags
                + " requestId=" + requestId)
        Log.d(TAG, responseString)
    }

    /**
     * listTags() �Ļص�������
     *
     * @param context   ������
     * @param errorCode �����롣0��ʾ�о�tag�ɹ�����0��ʾʧ�ܡ�
     * @param tags      ��ǰӦ�����õ�����tag��
     * @param requestId ������������͵������id
     */
    override fun onListTags(
        context: Context,
        errorCode: Int,
        tags: List<String>,
        requestId: String
    ) {
        val responseString = ("onListTags errorCode=" + errorCode + " tags="
                + tags)
        Log.d(TAG, responseString)

        // Demo���½���չʾ���룬Ӧ��������������Լ��Ĵ����߼�
    }

    /**
     * PushManager.stopWork() �Ļص�������
     *
     * @param context   ������
     * @param errorCode �����롣0��ʾ�������ͽ�󶨳ɹ�����0��ʾʧ�ܡ�
     * @param requestId ������������͵������id
     */
    override fun onUnbind(
        context: Context,
        errorCode: Int,
        requestId: String
    ) {
        val responseString = ("onUnbind errorCode=" + errorCode
                + " requestId = " + requestId)
        Log.d(TAG, responseString)
        if (errorCode == 0) {
            // ��󶨳ɹ�
            Log.d(TAG, "���ɹ�")
        }
        // Demo���½���չʾ���룬Ӧ��������������Լ��Ĵ����߼�
    }

    companion object {
        /**
         * TAG to Log
         */
        val TAG = MyPushMessageReceiver::class.java
            .simpleName
    }
}