package com.cx.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 时间工具类
 *
 * @author Administrator·
 */
public class DateUtil {
    public static final String FULL_TIME_PATTERN = "yyyyMMddHHmmss";
    public static final String FULL_TIME_PATTERN_YY = "yyMMddHHmmss";
    public static final String FULL_TIME_PATTERN_YYYY = "yyyyMMdd";
    public static final String FULL_TIME_SPLIT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_SPLIT_PATTERN = "yyyy-MM-dd";
    public static final String CST_TIME_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";

    private DateUtil() {
    }

    public static String formatFullTime(LocalDateTime localDateTime) {
        return formatFullTime(localDateTime, FULL_TIME_PATTERN);
    }

    public static String formatFullTime(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(dateTimeFormatter);
    }

    public static String formatFullTime(LocalDate localDate, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDate.format(dateTimeFormatter);
    }

    public static String getDateFormat(Date date, String dateFormatType) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatType, Locale.CHINA);
        return simpleDateFormat.format(date);
    }

    public static String formatCstTime(String date, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CST_TIME_PATTERN, Locale.US);
        Date usDate = simpleDateFormat.parse(date);
        return DateUtil.getDateFormat(usDate, format);
    }

    public static String formatInstant(Instant instant, String format) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }

    public static LocalDateTime formatLocalDateTime(String date, String format) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(format));
    }

    public static LocalDate formatLocalDate(String date, String format) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
    }

    public static String getFullTime(){
        return LocalDateTime.parse(LocalDateTime.now().toString(), DateTimeFormatter.ofPattern(DateUtil.FULL_TIME_SPLIT_PATTERN)).toString();
    }

    public static void main(String[] args) {
        LocalDateTime s=LocalDateTime.now();
        System.out.println(DateUtil.formatLocalDateTime(s.toString(),DateUtil.FULL_TIME_SPLIT_PATTERN));
    }

    /**
     * 获取两个日期字符串之间的日期集合
     *
     * @param startTime:String
     * @param endTime:String
     * @return list:yyyy-MM-dd
     */
    public static List<String> getBetweenDate(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 声明保存日期集合
        List<String> list = new ArrayList<String>();
        try {
            // 转化成日期类型
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);

            //用Calendar 进行日期比较判断
            Calendar calendar = Calendar.getInstance();
            while (startDate.getTime() <= endDate.getTime()) {
                // 把日期添加到集合
                list.add(sdf.format(startDate));
                // 设置日期
                calendar.setTime(startDate);
                //把日期增加一天
                calendar.add(Calendar.DATE, 1);
                // 获取增加后的日期
                startDate = calendar.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取时间段内的星期几
    public static List<String> getDutyDays(String rest,String strStartDate, String strEndDate) {
        List<String> list = new ArrayList<>();//要返回的时间集合
        LocalDate startDate = formatLocalDate(strStartDate, DATE_SPLIT_PATTERN);
        LocalDate endDate = formatLocalDate(strEndDate, DATE_SPLIT_PATTERN);
        endDate = endDate.plusDays(1L);
        while (startDate.isBefore(endDate)) {
            if (rest.contains(startDate.getDayOfWeek().getValue()+"")){
                list.add(startDate.toString());
            }
            startDate=startDate.plusDays(1L);
         }
        return list;
    }

    public static String StringToLong(String dateTime) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat(DATE_SPLIT_PATTERN).parse(dateTime));
        return calendar.getTimeInMillis()+"";
    }
}
