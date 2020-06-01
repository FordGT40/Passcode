package com.wisdom.passcode.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StrUtils {

    static DecimalFormat df = new DecimalFormat("#0.00");

    public final static boolean isBlank(String str) {
        return null == str || str.equals("");
    }

    /**
     * 处理空字符串
     *
     * @param str
     * @return String
     */
    public static String doEmpty(String str) {
        return doEmpty(str, "");
    }

    public static String decimalFromart(double value) {
        return df.format(value);
    }

    /**
     * 判断一个字符是否是中文
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;// 根据字节码判断
    }

    /**
     * 判断一个字符是否包含中文
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 判断一个字符是否是中文
     *
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        char[] strChar = str.toCharArray();
        Boolean isChiness = false;
        for (char a : strChar) {
            // 根据字节码判断
            isChiness = a >= 0x4E00 && a <= 0x9FA5;
        }
        return isChiness;
    }


    /**
     * 判断一个字符串是否含有中文
     *
     * @param str
     * @return
     */
    public static boolean isHasChinese(String str) {
        if (str == null) return false;
        for (char c : str.toCharArray()) {
            if (isChinese(c)) return true;// 有一个中文字符就返回
        }
        return false;
    }


    /**
     * 处理空字符串
     *
     * @param str
     * @param defaultValue
     * @return String
     */
    public static String doEmpty(String str, String defaultValue) {
        if (str == null || str.equalsIgnoreCase("null")
                || str.trim().equals("")) {
            str = defaultValue;
        } else if (str.startsWith("null")) {
            str = str.substring(4, str.length());
        }
        return str.trim();
    }

    /**
     * 编码
     *
     * @param value
     * @return
     */
    public static String encoder(String value) {
        if (StrUtils.isBlank(value)) {
            return "";
        } else {
            return URLEncoder.encode(value);
        }
    }

    /**
     * 解码
     *
     * @param str
     * @return
     */
    public static String decoder(String str) {

        return URLDecoder.decode(str);
    }

    public static String inputStream2String(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }

    public static void showToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context context, int strId) {
        Toast.makeText(context, strId, Toast.LENGTH_LONG).show();
    }

    /**
     * 判断字符串文本是否为空
     *
     * @param str 待判断的字符串
     * @return 为空返回true  不为空返回 false
     */
    public static boolean isEmpty(String str) {
        boolean isEmpty = false;
        if (str.equals("") || str == null) {
            isEmpty = true;

        }
        return isEmpty;
    }


    /**
     * 验证电话号码是否正确的正则方法
     *
     * @param phoneNum
     * @return
     */
    public static boolean isPhoneNumberValid(String phoneNum) {
        boolean isvalid = false;
        String expression = "((^(13|15|18|17)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
        CharSequence inputStr = phoneNum;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isvalid = true;
        }
        return isvalid;
    }

//    "^[0-9]{0,9}:[0-9]{0,9}$"

    /**
     * 验证积分比值格式的正则方法  1：1
     *
     * @param integeralRatio
     * @return
     */
    public static boolean isIntegeralRatioValid(String integeralRatio) {
        boolean isvalid = false;
        String expression = "^[0-9]{1,9}:[0-9]{1,9}$";
        CharSequence inputStr = integeralRatio;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isvalid = true;
        }
        return isvalid;
    }


    /**
     * 判断一个字符是否是中文
     * @param c
     * @return
     */
//    public static boolean isHasChinese(char c) {
//        return c >= 0x4E00 &&  c <= 0x9FA5;// 根据字节码判断
//    }

    /**
     * 取得文本框的内容
     *
     * @param editText
     * @return
     */
    public static String getEdtTxtContent(EditText editText) {
        return editText.getText().toString().trim();
    }

    /**
     * 判断输入框的内容是否为空
     *
     * @param editText
     * @return editText为空返回true
     */
    public static Boolean isEdtTxtEmpty(EditText editText) {
        Boolean isEmpty = true;
        if (!getEdtTxtContent(editText).trim().equals("")) {
            isEmpty = false;
        }
        return isEmpty;
    }

    /**
     * 返回字符串的第一个字母
     *
     * @param str
     * @return
     */
    public static String getFirstChar(String str) {
        String strFirst = "";
        if (str.length() > 0) {
            strFirst = String.valueOf(str.charAt(0));
        }
        return strFirst;
    }

    /**
     * 判断str中是否包含str2
     *
     * @param str1
     * @param str2
     * @return
     */
    public static Boolean isStr1ContainStr2(String str1, String str2) {
        if (str1.indexOf(str2) == -1) {
            return true;
        } else {
            return false;
        }
    }

    //邮箱验证
    public static boolean isEmail(String strEmail) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(strEmail);
        Log.e("邮箱验证", m.matches() + "");
        if (m.matches() == true) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * 根据身份证的号码算出当前身份证持有者的性别和年龄 18位身份证
     *
     * @return
     */
    public static Map<String, Object> getCarInfo(String CardCode)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String year = CardCode.substring(6).substring(0, 4);// 得到年份
        String yue = CardCode.substring(10).substring(0, 2);// 得到月份
        String day = CardCode.substring(12).substring(0, 2);//得到日
        String sex;
        if (Integer.parseInt(CardCode.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
            sex = "女";
        } else {
            sex = "男";
        }
        Date date = new Date();// 得到当前的系统时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String fyear = format.format(date).substring(0, 4);// 当前年份
        String fyue = format.format(date).substring(5, 7);// 月份
        // String fday=format.format(date).substring(8,10);
        int age = 0;
        if (Integer.parseInt(yue) <= Integer.parseInt(fyue)) { // 当前月份大于用户出身的月份表示已过生
            age = Integer.parseInt(fyear) - Integer.parseInt(year) + 1;
        } else {// 当前用户还没过生
            age = Integer.parseInt(fyear) - Integer.parseInt(year);
        }
        String birth = year + "年" + yue + "月" + day + "日";
        map.put("sex", sex);
        map.put("age", age);
        map.put("birth", birth);
        return map;
    }

    /**
     * 获取当前ip地址
     *
     * @param context
     * @return
     */
    public static String getLocalIpAddress(Context context) {
        try {

            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            return int2ip(i);
        } catch (Exception ex) {
            return " 获取IP出错鸟!!!!请保证是WIFI,或者请重新打开网络!\n" + ex.getMessage();
        }
        // return null;
    }
    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }




}
