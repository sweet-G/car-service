package com.zt.tms;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author zhangtian
 * @date 2018/8/31
 */

public class Mytest {

    public static void main(String[] args) {
        String p = "000000";
        String codec = DigestUtils.md5Hex(p);
        System.out.println(codec);
    }
}
