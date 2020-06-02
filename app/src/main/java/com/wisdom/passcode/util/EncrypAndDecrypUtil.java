package com.wisdom.passcode.util;


import android.util.Base64;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author HanXueFeng
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.util
 * @class describe：BlowFish加解密工具类
 * @time 2020/5/14 0014 10:25
 * @change
 */
public class EncrypAndDecrypUtil {

    // 密钥

    public static final String ENCRYPT_KEY = "WkoxWT0kJik=";

    // 初始化向量

    public static final String INITIALIZATION_VECTOR = "cnBHdE9F";

    // 转换模式

    public static final String TRANSFORMATION = "Blowfish/CBC/PKCS5Padding";

    // 密钥算法名称

    public static final String BLOWFISH = "Blowfish";


    /**
     * 加密
     *
     * @param text 加密文本
     */

    public static String encrypt(String text)

            throws Exception {
        String key = ENCRYPT_KEY;
        String initializationVector = INITIALIZATION_VECTOR;
        // 根据给定的字节数组构造一个密钥  Blowfish-与给定的密钥内容相关联的密钥算法的名称
        SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes("UTF-8"), BLOWFISH);
        // 使用 initializationVector 中的字节作为 IV 来构造一个 IvParameterSpec 对象
        AlgorithmParameterSpec iv = new IvParameterSpec(initializationVector.getBytes("UTF-8"));

        // 返回实现指定转换的 Cipher 对象
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);

        // 用密钥和随机源初始化此 Cipher
        cipher.init(Cipher.ENCRYPT_MODE, sksSpec, iv);

        // 加密文本
        byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));


        return Base64.encodeToString(encrypted, Base64.NO_WRAP);
    }
    public static String encrypt(EditText editText)

            throws Exception {
        String key = ENCRYPT_KEY;
        String initializationVector = INITIALIZATION_VECTOR;
        // 根据给定的字节数组构造一个密钥  Blowfish-与给定的密钥内容相关联的密钥算法的名称
        SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes("UTF-8"), BLOWFISH);
        // 使用 initializationVector 中的字节作为 IV 来构造一个 IvParameterSpec 对象
        AlgorithmParameterSpec iv = new IvParameterSpec(initializationVector.getBytes("UTF-8"));

        // 返回实现指定转换的 Cipher 对象
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);

        // 用密钥和随机源初始化此 Cipher
        cipher.init(Cipher.ENCRYPT_MODE, sksSpec, iv);

        // 加密文本
        byte[] encrypted = cipher.doFinal(editText.getText().toString().getBytes("UTF-8"));


        return Base64.encodeToString(encrypted, Base64.NO_WRAP);
    }


    /**
     * 解密
     *
     * @param text 加密文本
     */

    public static String decrypt(String text)
            throws Exception {
        String key = ENCRYPT_KEY;
        String initializationVector = INITIALIZATION_VECTOR;

        byte[] encrypted = null;
        try {
            encrypted = Base64.decode(text, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SecretKeySpec skeSpect = new SecretKeySpec(key.getBytes(), BLOWFISH);

        AlgorithmParameterSpec iv = new IvParameterSpec(initializationVector.getBytes());

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);

        cipher.init(Cipher.DECRYPT_MODE, skeSpect, iv);

        byte[] decrypted = cipher.doFinal(encrypted);

        return new String(decrypted);

    }


    /**
     * Base64字符解码
     *
     * @param base64String -- 被解码字符
     * @return 解码后字符
     */

    public static String base64Decoder(String base64String) {

        if (StrUtils.isEmpty(base64String)) {

            return base64String;

        } else {

            return new String(Base64.decode(base64String, Base64.NO_WRAP));

        }

    }


    /**
     * Base64字符编码
     *
     * @param sourceString -- 字符
     * @return 编码后字符
     */

    public static String base64Encoder(String sourceString) {
        if (StrUtils.isEmpty(sourceString)) {
            return sourceString;
        } else {
            return new String(Base64.encode(sourceString.getBytes(), Base64.NO_WRAP));
        }
    }


    /**
     * 获取  SHA1 的值
     *
     * @param message
     * @return
     */
    public static String SHA1(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            return hex(md.digest(message.getBytes("CP1252")));
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    private static String hex(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (byte b : array) {
            sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }


    /**
     * Md5加密
     *
     * @param args
     * @return
     */

    public static String MD5(String args) {
        return MD5Util.MD5(args);
    }
}
