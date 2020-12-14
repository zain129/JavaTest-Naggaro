/**
 * @author Zain I.
 * created on 14/12/2020
 **/

package com.zainimtiaz.nagarro.util;

import java.util.List;

public class GeneralUtil {

    public static boolean isNullorEmpty(String string) {
        return (string == null || string.trim().isEmpty());
    }

    public static boolean isNullorEmpty(List list) {
        return (list == null || list.size() < 1);
    }
}
