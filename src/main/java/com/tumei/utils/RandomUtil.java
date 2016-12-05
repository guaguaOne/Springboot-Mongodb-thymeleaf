package com.tumei.utils;

import java.util.Base64;
import java.util.Random;

/**
 * Created by Administrator on 2016/11/24 0024.
 */
public class RandomUtil {
    /**
     * 随机数据
     * @return
     */
    public static int getRandom() {
        Random random = new Random();
        return random.nextInt();
    }

    /**
     * 获取16bit随机数
     * @return
     */
    public static String randomDigest() {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        byte[] bytes = new byte[16];
        long prefix = random.nextLong();
        for (int i = 0; i < 8; ++i) {
            bytes[i] = (byte) (prefix >> (7 - i << 3));
        }

        long suffix = random.nextLong();
        for (int i = 0; i < 8; ++i) {
            bytes[i + 8] = (byte) (suffix >> (7 - i << 3));
        }

        return Base64.getEncoder().encodeToString(bytes);
    }
}
