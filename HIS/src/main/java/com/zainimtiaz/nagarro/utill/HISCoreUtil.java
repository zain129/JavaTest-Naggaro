package com.zainimtiaz.nagarro.utill;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.zainimtiaz.nagarro.wrapper.response.BranchResponseWrapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;

        /*
         * @author    : irfan
         * @Date      : 16-Apr-18
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
         * @FileName  : HISCoreUtil
         *
         * Copyright ©
         * SolutionDots,
         * All rights reserved.
         *
         */

public class HISCoreUtil {

    public static boolean isNull(String checkString) {
        if (null == checkString || checkString.trim().length() == 0 || checkString.trim().equalsIgnoreCase("null")) {
            return true;
        }
        return false;
    }

    public static boolean isValidObject(Object object) {
        if (null != object) {
            return true;
        }
        return false;
    }

    public static boolean isListEmpty(List<?> dataList) {
        if (null == dataList || dataList.isEmpty()) {
            return true;
        }
        return false;
    }

    /***
     * Valid mean , not null and not empty the list
     *
     *
     */
    public static boolean isListValid(List<?> dataList) {
        if (null == dataList || dataList.isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean isMapEmpty(Map<?, ?> dataMap) {
        if (null == dataMap || dataMap.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isSetEmpty(Set<?> dataSet) {
        if (null == dataSet || dataSet.isEmpty()) {
            return true;
        }
        return false;
    }

    public static long convertDateToMilliSeconds(String myDate) {
        //= "2014/10/29 18:10:45";
        if (myDate != null) {
            Instant instant = Instant.parse(myDate);

            return instant.toEpochMilli();
        } else {
            return 0;
        }
    }

    public static Date convertToTime(String str) {
        Date date = null;
        if (str != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(HISConstants.TIME_FORMAT_PATTERN);
            try {
                date = formatter.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return date;
    }

    public static String convertTimeToString(Date time) {
        String formatedDate = null;
        if (time != null) {
            SimpleDateFormat form = new SimpleDateFormat("hh:mm:ss");
            formatedDate = form.format(time);
        }
        return formatedDate;
    }

    public static String convertTimeToString(Date time, String format) {
        String formatedDate = null;
        if (time != null) {
            SimpleDateFormat form = new SimpleDateFormat(format);
            formatedDate = form.format(time);
        }
        return formatedDate;
    }

    public static String convertDateToString(Date date) {
        String formatedDate = null;
        if (date != null) {
            SimpleDateFormat form = new SimpleDateFormat("dd MMMM yyyy");
            formatedDate = form.format(date);
        }
        return formatedDate;
    }

    public static String convertDateToString(Date date,String formate) {
        String formatedDate = null;
        if (date != null) {
            SimpleDateFormat form = new SimpleDateFormat(formate);
            formatedDate = form.format(date);
        }
        return formatedDate;
    }

    public static Date convertToDate(String str) {
        Date date = null;
        if (str != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            try {
                date = formatter.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return date;
    }
    public static LocalDateTime convertToLocalDateTimeViaSqlTimestamp(Date dateToConvert) {
        if(dateToConvert !=null){
            return new java.sql.Timestamp(
                    dateToConvert.getTime()).toLocalDateTime();
        }
        else{
            return null;
        }
    }
    public static Date convertToDateViaLocalDateTime(LocalDateTime dateToConvert) {
        Date dateFromLocalDateTime =null;
        if(dateToConvert !=null)
            dateFromLocalDateTime = java.sql.Timestamp.valueOf(dateToConvert);
        return dateFromLocalDateTime;
    }

    public static Date convertToDateWithTime(String strDate, String formateDate) {
        Date date = null;
        if (strDate != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(formateDate);///*"yyyy-MM-dd'T'HH:mm"*/
            try {
                date = formatter.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return date;
    }


    public static Date addTimetoDate(Date str, long duration) {
        Date date = null;
        if (str != null) {
            date = Date.from(str.toInstant().plusSeconds(duration * 60));
        }
        return date;
    }

    public static String convertDateToStringWithZone(Date date) {
        String formatedDate = null;
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            formatedDate = formatter.format(date);
            //date = formatter.parse(str);
        }
        return formatedDate;
    }

    public static String convertDateAndTimeToString(Date date) {
        String formatedDate = null;
        if (date != null) {
            SimpleDateFormat form = new SimpleDateFormat("dd MMMM yyyy : hh:mm:ss");
            formatedDate = form.format(date);
        }
        return formatedDate;
    }
    public static String convertDateAndTimeToStringWithPMAndAM(Date date) {
        String formatedDate = null;
        if (date != null) {
            SimpleDateFormat form = new SimpleDateFormat("dd MMMM yyyy : hh:mm:ss aa");
            formatedDate = form.format(date);
        }
        return formatedDate;
    }
    public static Date convertToDateOnly(String date) {
        Date formatedDate = null;
        if (date != null) {
            SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
            try {
                formatedDate = form.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return formatedDate;
    }



    public static Predicate<BranchResponseWrapper> isExist(long id) {
        return p->(p.getId() ==id);
    }

    public static String  getCurrentDateWithZone(String ZoneName,String format){
  //      System.out.println("-----Current Date  of a  time zone using -----");
        ZoneId zoneId = ZoneId.of(ZoneName);
        LocalTime localTime=LocalTime.now(zoneId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String formattedTime=localTime.format(formatter);
  //      System.out.println("Current Date : " + formattedTime);
        return formattedTime;
    }


    public static String convertDateToTimeZone(Date date, String format,
                                               String timeZone) {

        if (date == null)
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (timeZone == null || "".equalsIgnoreCase(timeZone.trim())) {
            return null;
        }
        sdf.setTimeZone(java.util.TimeZone.getTimeZone(timeZone));
        return  sdf.format(date);

        //    return

    }


    public static String  getCurrentTimeByzone(String tzName) {

        String currentTime="";

        if(isNull(tzName)){
            return null;
        }
        Calendar time = new GregorianCalendar(TimeZone.getTimeZone(tzName));
        time.setTimeInMillis(time.getTimeInMillis());
        int hour = time.get(Calendar.HOUR);
        int minute = time.get(Calendar.MINUTE);
        int second = time.get(Calendar.SECOND);
        int year = time.get(Calendar.YEAR);
        currentTime=hour+":"+minute+":"+second;
        return currentTime;
    }

    public static String formatDateToString(Date date, String format, String timeZone) {

        if (date == null)
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        if (timeZone == null || "".equalsIgnoreCase(timeZone.trim())) {
            return null;
        }

        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));

        return sdf.format(date);
    }

    public static Date convertToDateZone(String str) {
        Date date = null;
        if (str != null) {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                date = formatter.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return date;
    }

    public static String convertToTimeFormat(String str,String timeFormat) {

        LocalTime time = LocalTime.parse(str);
    //    System.out.println(time);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);
   //     System.out.println(formatter.format(time));
        return  formatter.format(time);
    }
    public static LocalDate convertDateToLocalDate(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Date convertLocalDateToDate(LocalDate dateToConvert) {
        if (dateToConvert != null) {
            return java.sql.Date.valueOf(dateToConvert);
        } else
            return null;
    }
    public static Date convertToAPPDate(String str) {
        Date date = null;
        if (str != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(HISConstants.DATE_FORMAT_APP);
            try {
                date = formatter.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return date;
    }
    public static Date convertToAPPDateZone(String str,String format) {
        Date date = null;
        if (str != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            try {
                date = formatter.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return date;
    }


    public static Date convertToDateString(String str,String format) {
        Date date = null;
        if (str != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            try {
                date = formatter.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return date;
    }


    public static Date convertToDateNew(String str) {
        Date date = null;
        if (str != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
            try {
                date = formatter.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return date;
    }

    public static Date  convertStringDateObject(String dateinString) {
       Date dte=new Date();
        if (dateinString != null) {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                dte = formatter.parse(dateinString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dte;
    }

    public static Date convertToDateDetail(String str,String format) {
        Date date = null;
        if (str != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            try {
                date = formatter.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return date;
    }

    public static String convertDateToStringUpload(Date date) {
        String formatedDate = null;
        if (date != null) {
            SimpleDateFormat form = new SimpleDateFormat("ddMMyyyyHHss");
            formatedDate = form.format(date);
        }
        return formatedDate;
    }

    public static File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new File( multipart.getOriginalFilename());
        multipart.transferTo(convFile);
        return convFile;
    }


    /*public static Date convertDateToStringWithZoneDate(Date date) {
        String formatedDate = null;
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
           // formatedDate = formatter.format(date);
            //date = formatter.parse(str);
        }
        return formatter;
    }*/

    public static boolean containsDigit(String s) {
        boolean containsDigit = false;
        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (containsDigit = Character.isDigit(c)) {
                    break;
                }
            }
        }
        return containsDigit;
    }
    public static boolean isTimeBetweenTwoTime(String argStartTime,
                                               String argEndTime, String argCurrentTime) throws ParseException {
        String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
        //
     /*   if (argStartTime.matches(reg) && argEndTime.matches(reg)
                && argCurrentTime.matches(reg)) {*/
        boolean valid = false;
        // Start Time
        java.util.Date startTime = new SimpleDateFormat("HH:mm")
                .parse(argStartTime);
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startTime);
        java.util.Date currentTime = new SimpleDateFormat("HH:mm")
                .parse(argCurrentTime);
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(currentTime);
        java.util.Date endTime = new SimpleDateFormat("HH:mm")
                .parse(argEndTime);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endTime);
        if (currentTime.compareTo(endTime) < 0) {

            currentCalendar.add(Calendar.DATE, 1);
            currentTime = currentCalendar.getTime();

        }

        if (startTime.compareTo(endTime) < 0) {
            startCalendar.add(Calendar.DATE, 1);
            startTime = startCalendar.getTime();
        }

        if (currentTime.before(startTime)) {
            valid = false;
        } else {

            if (currentTime.after(endTime)) {
                endCalendar.add(Calendar.DATE, 1);
                endTime = endCalendar.getTime();
            }

            if (currentTime.before(endTime)) {
                valid = true;
            } else {
                valid = false;
            }

        }
        return valid;

        /*} else {
            throw new IllegalArgumentException(
                    "Not a valid time, expecting HH:mm format");
        }
*/
    }

    public static String getDayFromDate(String in_date){
        SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd");
        Date dt1= null;
        try {
            dt1 = format1.parse(in_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat format2=new SimpleDateFormat("EEEE");
        String finalDay=format2.format(dt1);
        return finalDay;
    }
    public static String convertTimeTozone(String time,String zone){
        Date date = HISCoreUtil.convertToTime(time);
        String format ="HH:mm";
        String convertedResult = HISCoreUtil.convertDateToTimeZone(date,format,zone);
        return convertedResult;
    }



    public  static LocalDate convertToDateLocal(String dateInString){
      //  String string = "January 2, 2010";
        LocalDate date=null;
        if (dateInString != null) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH);
         date = LocalDate.parse(dateInString, formatter);
   //     System.out.println(date);
        }
        return date;
    }


    public static Date  convertStringDateObjectTax(String dateinString) {
        Date dte=new Date();
        if (dateinString != null) {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                dte = formatter.parse(dateinString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dte;
    }


    public static String convertDateToStringWithDateDisplay(Date dateinString,String format) {
        String dte="";
        if (dateinString != null) {


            try {
                SimpleDateFormat formatter = new SimpleDateFormat(format);
                 dte = formatter.format(dateinString);
              //  dte = formatter.parse(dateinString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dte;
    }


    public static int adjustOneOrTwoDigitYearInput(int inputYear, int referenceYear) {
        if(inputYear > 99) {
            return inputYear;
        }

        int currentCentury = referenceYear / 100 * 100;
        int currentCenturyYear = currentCentury + inputYear;
        int upperLimit = referenceYear + 20;
        int lowerLimit = referenceYear - 79;

        // initially place it in current century
        int adjusted = currentCenturyYear;

        if(adjusted> upperLimit) {
            // shift a century down
            adjusted -= 100;
        }
        else if(adjusted < lowerLimit) {
            // shift a century up
            adjusted += 100;
        }

        return adjusted;
    }

    public static String formatCurrencyDisplay(double amount, String format) {
        String returnFormat = "";

        if (format.equals("123,456")) {
            String pattern = "###,###";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);
            returnFormat= decimalFormat.format(amount);
        } else if (format.equals("123,456.00")) {
            String pattern = "###,###.00";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);
            returnFormat = decimalFormat.format(amount);
        } else if (format.equals("123456")) {

            returnFormat = new DecimalFormat("###").format(amount);
            return returnFormat;
        } else if (format.equals("123456.00")) {

            returnFormat = new DecimalFormat("###.00").format(amount);

            return returnFormat;

        }
        return returnFormat;
    }

    public static Date  convertStringDateObjectLabOrder(String dateinString,String format) {
        Date dte=new Date();
        if (dateinString != null) {

            SimpleDateFormat formatter = new SimpleDateFormat(format);
            try {
                dte = formatter.parse(dateinString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dte;
    }

    public static Date  convertStringDateObjectOrder(String dateinString) {
        Date dte=new Date();
        if (dateinString != null) {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                dte = formatter.parse(dateinString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dte;
    }


    public static String  getTimeFromString(String myTime,String format) {

      //  String myTime = "10:30:54";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(myTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedTime = sdf.format(date);
        return formattedTime;
       // System.out.println(formattedTime);

    }

    public static String convertToHourFormat(String Time,String hoursFormat,String timeFormat) {
        if(timeFormat.equals("hh:mm:ss")){
        if(hoursFormat.equals("12")){
            timeFormat="hh:mm:ss";
        }
        }else if(timeFormat.equals("hh:mm")){
          if(hoursFormat.equals("12")){
              timeFormat="hh:mm";
          }
        }
        if(timeFormat.equals("hh:mm:ss")){
            if(hoursFormat.equals("24")){
                timeFormat="HH:mm:ss";
            }
        }else if(timeFormat.equals("hh:mm")){
            if(hoursFormat.equals("24")){
                timeFormat="HH:mm";
            }
        }
        DateFormat f1 = new SimpleDateFormat(timeFormat); //11:00 pm
        Date d = null;
        try {
            d = f1.parse(Time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DateFormat f2 = new SimpleDateFormat(timeFormat);
        String x = f2.format(d); // "23:00"

        return x;
    }


    public static int calculateAge(Date birthday, Date date) {
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int d1 = Integer.parseInt(formatter.format(birthday));
        int d2 = Integer.parseInt(formatter.format(date));
        int age = (d2-d1)/10000;
        return age;
    }

    public static String convertDateAndTimeToStringNew(Date date) {
        String formatedDate = null;
        if (date != null) {
            SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            formatedDate = form.format(date);
        }
        return formatedDate;
    }


    public static byte[] getBarCodeImage(String text, int width, int height) {
        try {
            Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            Writer writer = new Code128Writer();
            BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.CODE_128, width, height);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

}