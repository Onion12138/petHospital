package com.ecnu.six.pethospital.oauth.utils;

import org.springframework.util.DigestUtils;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * @author LEO D PEN
 * @date 2021
 * @desc
 * @since
 */
public class MD5Utils {

    private static final String SALT = "pet_";

    public static String pwdMd5(String original) {
        return DigestUtils.md5DigestAsHex((SALT + original).getBytes());
    }

    public static String TokenUtil(String stuId) {
        Timestamp timestamp = Timestamp.from(Instant.now());
        String token = stuId + "#" + timestamp.toString();
        return token;
    }



}
