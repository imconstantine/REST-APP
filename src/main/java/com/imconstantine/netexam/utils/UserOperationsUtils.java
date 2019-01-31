package com.imconstantine.netexam.utils;

import com.imconstantine.netexam.exception.NetExamException;
import org.apache.commons.codec.digest.DigestUtils;

public class UserOperationsUtils {

    public static String passwordToHashKey(String password) {
        return DigestUtils.md5Hex(password).toUpperCase();
    }

    public static void compareHashKeys(String firstHashKey, String secondHashKey) throws NetExamException {
        if (firstHashKey != null && secondHashKey != null) {
            if (!firstHashKey.equals(secondHashKey)) {
                throw new NetExamException(ErrorCode.PASSWORD_NOT_MATCH);
            }
        }
    }
}
