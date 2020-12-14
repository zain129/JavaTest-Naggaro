package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.enums.DateFormatEnum;
import com.zainimtiaz.nagarro.enums.ModuleEnum;
import com.sd.his.model.*;
import com.sd.his.repository.*;
import com.zainimtiaz.nagarro.model.Prefix;
import com.zainimtiaz.nagarro.repository.PrefixRepository;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class HISUtilService {
    @Autowired
    private PrefixRepository prefixRepository;

    private final Logger logger = LoggerFactory.getLogger(HISUtilService.class);

    /*
    * this method used where you need to get current prefix and update sequence of that prefix
    * used where you need both operation
    * */
    public String getPrefixId(ModuleEnum moduleName) {
        String currentPrefix = generatePrefix(moduleName);
        updatePrefix(moduleName);
        return currentPrefix;
    }

    /*
    this method use for getting prefix on popup and will not update current sequence in database
    * */
    public String generatePrefix(ModuleEnum moduleName) {
        Prefix prefix = prefixRepository.findByModule(moduleName.name());
        String currentPrefix = prefix.getName() + prefix.getCurrentValue();
        return currentPrefix;
    }

    /*
this method use for update current sequence in database after saving new entity in database
* */
    public void updatePrefix(ModuleEnum moduleName) {
        Prefix prefix = prefixRepository.findByModule(moduleName.name());
        prefix.setCurrentValue(prefix.getCurrentValue() + 1L);
        prefixRepository.save(prefix);
    }

    // Get List of  ALL TimeZones from  Java 8 TimeZone API
    public Map<String, String> getListofTimeZone() {

        List<String> zoneList = new ArrayList<>(ZoneId.getAvailableZoneIds());
        if (HISCoreUtil.isListValid(zoneList)) {
            Map<String, String> allZoneIds = getAllZoneIds(zoneList);
            return allZoneIds;
        } else {
            return null;
        }
    }

    // Using Map to Retrieve  TimeZone
    public Map<String, String> getAllZoneIds(List<String> zoneList) {

        Map<String, String> result = new HashMap<>();
        LocalDateTime dt = LocalDateTime.now();

        for (String zoneId : zoneList) {

            ZoneId zone = ZoneId.of(zoneId);
            ZonedDateTime zdt = dt.atZone(zone);
            ZoneOffset zos = zdt.getOffset();
            String offset = zos.getId().replaceAll("Z", "+00:00");
            result.put(zone.toString(), offset);

        }

        return result;

    }

    // this Method convert date with respect to timezone
    /*
       First Input Parameter Date Object
       Second Parameter timezone

     */
    public String converttoUTCDateTimeZone(Date date,
                                           String timeZone) {

        if (date == null)
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat(String.valueOf(DateFormatEnum.yyyyMMdd0000));


        if (timeZone == null || "".equalsIgnoreCase(timeZone.trim())) {
            return null;
        }

        sdf.setTimeZone(java.util.TimeZone.getTimeZone(timeZone));

        return sdf.format(date);
    }


    public String getCurrentTimeByzone(String tzName) {

        String currentTime = "";

        if (HISCoreUtil.isNull(tzName)) {
            return null;
        }
        Calendar time = new GregorianCalendar(java.util.TimeZone.getTimeZone(tzName));
        time.setTimeInMillis(time.getTimeInMillis());
        int hour = time.get(Calendar.HOUR);
        int minute = time.get(Calendar.MINUTE);
        int second = time.get(Calendar.SECOND);
        int year = time.get(Calendar.YEAR);
        currentTime = hour + ":" + minute + ":" + second;
        return currentTime;
    }

    // This Method  get Current Date time of Specific TimeZone
    //
    public String getDateTimefromTimeZone(Date date, String format, String timeZone) {

        if (date == null)
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        if (timeZone == null || "".equalsIgnoreCase(timeZone.trim())) {
            return null;
        }

        sdf.setTimeZone(java.util.TimeZone.getTimeZone(timeZone));
        return sdf.format(date);
    }


    private Date combineDateTime(Date date, Date time) {
        Calendar calendarA = Calendar.getInstance();
        calendarA.setTime(date);
        Calendar calendarB = Calendar.getInstance();
        calendarB.setTime(time);

        calendarA.set(Calendar.HOUR_OF_DAY, calendarB.get(Calendar.HOUR_OF_DAY));
        calendarA.set(Calendar.MINUTE, calendarB.get(Calendar.MINUTE));
        calendarA.set(Calendar.SECOND, calendarB.get(Calendar.SECOND));
        calendarA.set(Calendar.MILLISECOND, calendarB.get(Calendar.MILLISECOND));

        return calendarA.getTime();
    }

    public List<DateFormatEnum> getDateFormatList() {


        List<DateFormatEnum> dateFormatList =
                new ArrayList<DateFormatEnum>(EnumSet.allOf(DateFormatEnum.class));


        if (null == dateFormatList || dateFormatList.isEmpty()) {
            return null;
        } else {
            return dateFormatList;
        }


    }


    public String convertDateToTimeZone(Date date, String format, String timeZone) {
        if (date == null)
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        if (timeZone == null || "".equalsIgnoreCase(timeZone.trim())) {
            return null;
        }

        sdf.setTimeZone(java.util.TimeZone.getTimeZone(timeZone));
        return sdf.format(date);
    }


    public static String formatCurrencyDisplay(double amount, String format) {
        String returnFormat = "";
        if (format.equals("123,456")) {

            String pattern = "###,###";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);

            returnFormat= decimalFormat.format(amount);
     //       System.out.println("Currency Format"+returnFormat);
            //    DecimalFormat formatter = new DecimalFormat("###,000");
            //    returnFormat = formatter.format(Double.parseDouble(amount));
            // "###,###,##0.00"
        } else if (format.equals("123,456.00")) {

            String pattern = "###,###.00";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);

            returnFormat = decimalFormat.format(amount);
      //      System.out.println("Currency Format"+returnFormat);

            /*DecimalFormat formatter = new DecimalFormat("###,###.00");
            returnFormat = formatter.format(Double.parseDouble(amount));*/
        } else if (format.equals("123456")) {

            returnFormat = new DecimalFormat("###").format(amount);
      //      System.out.println(returnFormat);
            return returnFormat;
            //      DecimalFormat formatter = new DecimalFormat("######");
            //      returnFormat = formatter.format(Double.parseDouble(amount));
        } else if (format.equals("123456.00")) {

            returnFormat = new DecimalFormat("###.00").format(amount);
        //    System.out.println(returnFormat);
            return returnFormat;
            //     DecimalFormat formatter = new DecimalFormat("###.00");
            //    returnFormat = formatter.format(Double.parseDouble(amount));
        }
        return returnFormat;
    }

}
