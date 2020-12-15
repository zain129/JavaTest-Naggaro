/**
 * @author Zain I.
 * created on 14/12/2020
 **/

package com.zainimtiaz.nagarro.util;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class GeneralUtil {

    public static boolean isNullorEmpty(String string) {
        return (string == null || string.trim().isEmpty());
    }

    public static boolean isNullorEmpty(List list) {
        return (list == null || list.size() < 1);
    }

    public static String calculateHashValue(String string) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(string.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }
}
