package com.andcup.hades.hts.core.tools;

import java.security.MessageDigest;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/7.
 */
public class MD5 {

    public static String toMd5(String text) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception var8) {
            var8.printStackTrace();
            return "";
        }

        char[] charArray = text.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int md5Bytes = 0; md5Bytes < charArray.length; ++md5Bytes) {
            byteArray[md5Bytes] = (byte) charArray[md5Bytes];
        }

        byte[] var9 = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < var9.length; ++i) {
            int val = var9[i] & 255;
            if (val < 16) {
                hexValue.append("0");
            }

            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }
}
