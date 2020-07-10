package com.cloudbest.common.util;

import java.util.Random;
import java.util.UUID;

/**
 * 随机UUID工具
 * 效果：b5e8863950d649ffa5a372dd0e951416
 * @author scq
 *
 */
public class RandomUuidUtil {

    public static final String numberChar = "012345678";

    public static String getUUID(){
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr=str.replace("-", "");
        return uuidStr;
    }

    public static String generateNumString(int length) {

        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for ( int i = 0; i < length; i++ ) {
            sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
        }
        return sb.toString();
    }

    /*public static void main(String[] args) {
        System.out.println(getUUID());
    }*/
}
