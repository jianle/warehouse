package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class Utils {

    // 通过日期获取日期所在的周
    @SuppressWarnings("deprecation")
    public static String getWeekId(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int weekId = cal.get(Calendar.WEEK_OF_YEAR);
        
        if (getSaturdayOfTheWeek(date).getYear() > date.getYear()) {
            cal.setTime(DateUtils.addDays(date, -7));
            weekId = cal.get(Calendar.WEEK_OF_YEAR) + 1;
        }

        return String.format("%dW%02d", year, weekId);
    }

    public static Date getSaturdayOfTheWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int minus = 7 - dayOfWeek;
        c.add(Calendar.DATE, minus);
        return c.getTime();
    }
    
    public static String getMonthId(Date date) {
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int monthId = cal.get(Calendar.MONTH) + 1;

        return String.format("%dM%02d", year, monthId);
    }
    
    public static String monthToMonthId(String month) {
        
        if ("".equals(month)) {
            return getMonthId(new Date());
        }
        try {
            int year = Integer.valueOf(month.substring(0, 4));
            int monthId = Integer.valueOf(month.substring(5));
            return String.format("%dM%02d", year, monthId);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("monthToMonthId failed." + e);
            return getMonthId(new Date());
        }
        
    }
    
    public static String getMonthId(String dateStr) {
        
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            date = new Date();
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int monthId = cal.get(Calendar.MONTH) + 1;

        return String.format("%dM%02d", year, monthId);
    }
    
    public static void main(String[] args) throws ParseException {
        System.out.println(monthToMonthId("2015年12"));
    }

}
