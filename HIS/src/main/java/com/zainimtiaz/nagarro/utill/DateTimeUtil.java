package com.zainimtiaz.nagarro.utill;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/*
 * @author    :Tahir Mehmood
 * @Date      : 27-Jul-2018
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.ap.util
 * @FileName  : DateUtil
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public class DateTimeUtil {

    public static String convertDateToUFDateFormat(Date d, long millis) {
        if (!HISCoreUtil.isValidObject(d)) {
            d = new Date(millis);
        }

        String str = "";
        if (d != null) {

            Calendar start = Calendar.getInstance();
            start.setTime(d);
            Calendar end = Calendar.getInstance();
            end.setTime(new Date());

            SimpleDateFormat sameYrDateFormat = new SimpleDateFormat(HISConstants.DATE_PATTERN_SAME_YEAR),
                    preYrDateFormat = new SimpleDateFormat(HISConstants.DATE_FORMATE_ONE),
                    stf = new SimpleDateFormat(HISConstants.TIME_PATTERN);

            long diff = end.getTimeInMillis() - start.getTimeInMillis();

            if ((diff / 1000) <= 60) {
                str = "few seconds ago";
            } else if ((diff / 60000) <= 60) {
                str = (diff / 60000) + " minutes ago";
            } else if ((diff / 3600000) <= 24) {
                str = (diff / 3600000) + " hours ago";
            } else if ((diff / 3600000) > 24 && (diff / 3600000) < 48) {
                str = "Yesterday at " + stf.format(d).toLowerCase();
            } else if (start.getTime().getYear() != end.getTime().getYear()) {
                str = preYrDateFormat.format(d) + " at " + stf.format(d).toLowerCase();
            } else if ((diff / 31536000000L) <= 12) {
                str = sameYrDateFormat.format(d) + " at " + stf.format(d).toLowerCase();
            }
            //System.out.println("Date::: " + str);
        }
        return str;
    }

    public static long addDaysInMilliDays(long millisDate, int days) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millisDate);
        c.add(Calendar.DATE, days);

        return c.getTimeInMillis();
    }

    public static long getMillisFromStringDate(String date, String format) {
        Date parsedDate = null;
        try {
            SimpleDateFormat sameYrDateFormat = new SimpleDateFormat(format);
            parsedDate = sameYrDateFormat.parse(date);
        } catch (Exception ex) {

        }
        return parsedDate.getTime();
    }

    public static String getDateFromMillis(long dateMillis, String format) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sameYrDateFormat = null;
        try {
            calendar.setTimeInMillis(dateMillis);
            sameYrDateFormat = new SimpleDateFormat(format);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sameYrDateFormat.format(calendar.getTime());
    }

    public static Date getDateFromString(String dateString, String dateFormat) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        return format.parse(dateString);
    }

    public static String getFormattedDateFromDate(Date date, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        return format.format(date);
    }

    public static String convertDateFromTimeZoneToGMT(Date date, TimeZone timeZone, String toDateFormat) throws ParseException {
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat(toDateFormat);
        dateFormatGmt.setTimeZone(timeZone);

//        return dateFormatGmt.parse(dateFormatGmt.format(date));
        return dateFormatGmt.format(date);
    }

    public static int convertAppointmentTime(String time) {
        int temp = 0;
        if (time != null) {
            int pmResult = 0;
            String[] h1 = time.split(":");
            int hour = Integer.parseInt(h1[0]);
            int minute = Integer.parseInt(h1[1]);
            if (hour <= 12) {
                temp = minute + (60 * hour);
            } else {
                //logic will be change
                temp = minute + ((hour + 12) * 60);
            }
        }
        return temp;
    }

    public static String convertDateToGMT(String date) throws ParseException {
        if (date == null || date.trim().equals("")) return "";
        Date localDate = DateTimeUtil.getDateFromString(date, "E MMM dd yyyy HH:mm:ss");
        return DateTimeUtil.convertDateFromTimeZoneToGMT(localDate, TimeZone.getTimeZone("GMT"), HISConstants.DATE_FORMATE_THREE);
    }

    public static String convertDateToGMT(String date, String thisDateFormat) throws ParseException {
        if (date == null || date.trim().equals("")) return "";
        Date localDate = DateTimeUtil.getDateFromString(date, thisDateFormat);
        return DateTimeUtil.convertDateFromTimeZoneToGMT(localDate, TimeZone.getTimeZone("GMT"), HISConstants.DATE_FORMATE_THREE);
    }
}
