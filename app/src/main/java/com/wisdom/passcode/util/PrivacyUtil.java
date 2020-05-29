package com.wisdom.passcode.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author HanXueFeng
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.util
 * @class describe：
 * @time 2020/5/29 0029 17:05
 * @change
 */
public class PrivacyUtil {
    //银行账户：显示前六后四，范例：622848******4568
    public static String bankAcctDesensitization(String bankAcct) {
        if (bankAcct == null) {
            return "";
        }
        return replaceBetween(bankAcct, 6, bankAcct.length() - 4, null);
    }

    //身份证号码：显示前六后四，范例：110601********2015
    public static String idCardDesensitization(String idCard) {
        if (idCard == null) {
            return "";
        }
        return replaceBetween(idCard, 6, idCard.length() - 4, null);
    }

//    //客户email：显示前二后四，范例：abxx@xxx.com
//    public static String encryptEmail(String email) {
//        //判断是否为邮箱地址
//        if (email == null || !Pattern.matches(PatternUtil.EMAIL_REG, email)) {
//            return "";
//        }
//
//        StringBuilder sb = new StringBuilder(email);
//        //邮箱账号名只显示前两位
//        int at_position = sb.indexOf("@");
//        if (at_position > 2) {
//            sb.replace(2, at_position, StringUtils.repeat("x", at_position - 2));
//        }
//        //邮箱域名隐藏
//        int period_position = sb.lastIndexOf(".");
//        sb.replace(at_position + 1, period_position, StringUtils.repeat("x", period_position - at_position - 1));
//        return sb.toString();
//    }

    //手机：显示前三后四，范例：189****3684
    public static String phoneDesensitization(String phoneNo) {
        if (phoneNo == null) {
            return "";
        }
        return replaceBetween(phoneNo, 3, phoneNo.length() - 4, null);
    }





    /**
     * 将字符串开始位置到结束位置之间的字符用指定字符替换
     * @param sourceStr 待处理字符串
     * @param begin	开始位置
     * @param end	结束位置
     * @param replacement 替换字符
     * @return
     */
    private static String replaceBetween(String sourceStr, int begin, int end, String replacement) {
        if (sourceStr == null) {
            return "";
        }
        if (replacement == null) {
            replacement = "*";
        }
        int replaceLength = end - begin;
        if (StringUtils.isNotBlank(sourceStr) && replaceLength > 0) {
            StringBuilder sb = new StringBuilder(sourceStr);
            sb.replace(begin, end, StringUtils.repeat(replacement, replaceLength));
            return sb.toString();
        } else {
            return sourceStr;
        }
    }

    /**
     * 姓名脱敏算法
     * @param name
     * @return
     */
    public static String nameDesensitization(String name){
        if(name==null || name.isEmpty()){
            return "";
        }
        String myName = null;
        char[] chars = name.toCharArray();
        if(chars.length==1){
            myName=name;
        }
        if(chars.length==2){
            myName=name.replaceFirst(name.substring(0,1), "*");
        }
        if(chars.length>2){
            String str=name.substring(1, chars.length-1);
            String replacement="";
            for(int i=0;i<str.length();i++){
                replacement+="*";
            }
            myName=name.replaceAll(str, replacement);
        }
        return myName;
    }
}
