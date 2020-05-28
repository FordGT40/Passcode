package com.wisdom.passcode.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author HanXueFeng
 * @ProjectName project： hrbzwt
 * @class package：com.wisdom.hrbzwt.util
 * @class describe：
 * @time 2019/5/20 10:02
 * @change
 */
public class RandomUtil {

    /**
     * 生成指定位数位不重复的随机数，含数字+大小写
     *
     * @param bit 指定多少位的随机数（包括字母大小写和数字）
     * @return
     */
    public static String getRandomString(int bit) {
        StringBuilder bitString = new StringBuilder();
        //产生16位的强随机数
        Random rd = new SecureRandom();
        for (int i = 0; i < bit; i++) {
            //产生0-2的3位随机数
            int type = rd.nextInt(3);
            switch (type) {
                case 0:
                    //0-9的随机数
                    bitString.append(rd.nextInt(10));
                    break;
                case 1:
                    //ASCII在65-90之间为大写,获取大写随机
                    bitString.append((char) (rd.nextInt(25) + 65));
                    break;
                case 2:
                    //ASCII在97-122之间为小写，获取小写随机
                    bitString.append((char) (rd.nextInt(25) + 97));
                    break;
                default:
                    break;
            }
        }
        return bitString.toString();
    }

}
