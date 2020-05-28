package com.wisdom.passcode.util;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

/**
 * @author HanXueFeng
 * @ProjectName project： hrbzwt
 * @class package：com.wisdom.hrbzwt.util
 * @class describe：弹出对话框工具类
 * @time 2019/7/18 14:29
 * @change
 */
public class AlertUtil {
    /**
     * 消息弹出提示框
     *
     * @param context
     * @param message
     */
    public static void showMsgAlert(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage(message);
        builder.setPositiveButton("确定", null);
        builder.create().show();
    }

    /**
     * 消息弹出提示框
     *
     * @param context
     * @param message
     */
    public static void showMsgAlert(Context context, int messageId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage(context.getString(messageId));
        builder.setPositiveButton("确定", null);
        builder.create().show();
    }

    /**
     * 消息弹出提示框
     *
     * @param context
     * @param message
     */
    public static void showMsgAlert(Context context, String message, DialogInterface.OnClickListener onClickPositiveListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage(message);
        builder.setPositiveButton("确定", onClickPositiveListener);
        builder.create().show();
    }
    /**
     * 消息弹出提示框
     *
     * @param context
     * @param message
     */
    public static void showMsgAlert(Context context, int messageId, DialogInterface.OnClickListener onClickPositiveListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage(context.getString(messageId));
        builder.setPositiveButton("确定", onClickPositiveListener);
        builder.create().show();
    }
    /**
     * 消息弹出提示框
     *
     * @param context
     * @param message
     */
    public static void showMsgAlert(Context context, String message, DialogInterface.OnClickListener onClickNegativeListener, DialogInterface.OnClickListener onClickPositiveListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage(message);
        builder.setPositiveButton("确定", onClickPositiveListener);
        builder.setNegativeButton("取消", onClickNegativeListener);
        builder.create().show();
    }

    /**
     * @param context
     * @param positiveBtnStr          确定按钮文字
     * @param negativeBtnStr          取消按钮文字
     * @param message                 对话框信息
     * @param onClickPositiveListener 确定按钮点击事件
     * @param onClickNegativeListener 取消按钮点击事件
     */
    public static void showCustomAlert(Context context, String positiveBtnStr, String negativeBtnStr, String message, DialogInterface.OnClickListener onClickPositiveListener, DialogInterface.OnClickListener onClickNegativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage(message);
        builder.setPositiveButton(positiveBtnStr, onClickPositiveListener);
        builder.setNegativeButton(negativeBtnStr, onClickNegativeListener);
        builder.create().show();
    }

}
