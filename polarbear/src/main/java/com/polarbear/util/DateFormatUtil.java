package com.polarbear.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间与integer类型互换
 * 
 * @author
 * 
 */
public class DateFormatUtil {
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static final SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat ymdh = new SimpleDateFormat("yyyy-MM-dd HH");
	/**
	 * 得到月份第一天的初始时间
	 * @param month
	 * @return
	 */
	public static Integer getMonthsTime(int month) {
		Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
		Integer time = getCurrentSeconds(calendar.getTimeInMillis());
		return time;
	}
	
	public static Integer setDateHourTime(String date, Integer hour) throws ParseException {
		Date datetmp = ymd.parse(date);
		return setDateHourTime(datetmp, hour);
	}

	public static Integer setDateHourTime(Date date, Integer hour) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Integer time = getCurrentSeconds(calendar.getTimeInMillis());
		return time;
	}

	public static Integer setDateStartTime(Date date) {
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
		Integer time = getCurrentSeconds(calendar.getTimeInMillis());
		return time;
	}

	public static Integer getDateStartTime(String date) throws ParseException {
		Date datetmp = ymd.parse(date);
		return getDateStartTime(datetmp);
	}
	
	public static Integer getDateEndTime(String date) throws ParseException {
		Date datetmp = ymd.parse(date);
		return getDateEndTime(datetmp);
	}

	public static Integer getDateStartTime(Date date) {
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
		Integer time = getCurrentSeconds(calendar.getTimeInMillis());
		return time;
	}
	
	public static Integer getDateEndTime(Date date) {
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
		Integer time = getCurrentSeconds(calendar.getTimeInMillis());
		return time;
	}

	public static void main(String[] args) {		
//		System.out.println(getMonthsTime(-3));
//		System.out.println(dateToSeconds("2014-09-01 23:00:00"));
		try {
			System.out.println(secondsToDateString(setDateHourTime("2014-09-01",7)));
			System.out.println(secondsToDateString(setDateHourTime("2014-09-01",10)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(secondsToDateString(1411686000));
	}
	/**
	 * 格式 yyyy-MM-dd HH:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return sdf.format(date);
	}
	
	public static String formatDateH(Date date) {
		return ymdh.format(date);
	}
	
	public static Date parse(String date) throws ParseException {
		return sdf.parse(date);
	}

	/**
	 * 获取当前时间的秒数
	 * 
	 * @return Integer
	 */
	public static Integer getCurrentSeconds() {
		return getCurrentSeconds(System.currentTimeMillis());
	}

	/**
	 * 获取指定时间的n天后的毫秒数
	 * 
	 * @return Integer
	 */
	public static Integer getAfterDaySeconds(long millis, int n) {
		return Long.valueOf(millis + n * 24 * 60 * 60 * 1000).intValue();
	}

	/**
	 * 获取指定时间的n小时后的毫秒数
	 * 
	 * @return Integer
	 */
	public static Integer getAfterHoursSeconds(long millis, int n) {
		return Long.valueOf(millis  + n * 60 * 60 * 1000).intValue();
	}

	/**
	 * 获取指定时间的秒数
	 * 
	 * @param millis
	 * @return Integer
	 */
	public static Integer getCurrentSeconds(long millis) {
		return Long.valueOf(millis / 1000L).intValue();
	}

	/**
	 * 将秒数转为时间
	 * 
	 * @param seconds
	 * @return
	 */
	public static Date secondsToDate(Integer seconds) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(seconds * 1000L);
		return c.getTime();
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param strDate
	 *            字符串时间
	 * @param dateFormat
	 *            字符串的时间格式
	 * @return
	 */
	public static Date strToDate(String strDate, String dateFormat) {

		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = format.parse(strDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 将描述转化成本地时间
	 * 
	 * @param seconds
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String secondsToString(Integer seconds) {
		if (seconds == null) {
			return "";
		}
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(seconds * 1000L);
		if (c.getTime().toLocaleString().split(" ")[1].equals("0:00:00")) {
			return c.getTime().toLocaleString().split(" ")[0];
		} else {
			return c.getTime().toLocaleString();
		}
	}

	/**
	 * 将秒数转换成时间戳
	 * 
	 * @param seconds
	 * @return
	 */
	public static Timestamp secondToTimestamp(Integer seconds) {
		// System.out.println(getCurrentSeconds(getCurrentTimeMillis()));
		return new Timestamp(seconds * 1000L);
	}

	/**
	 * 将时间戳转换成呢过秒数
	 * 
	 * @param timestamp
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Integer timeStampToSecond(Timestamp timestamp) {
		return timestamp.getSeconds();
	}

	/**
	 * 将标准日期格式转成秒数
	 * 
	 * @param date
	 * @return
	 */
	public static Integer dateToSeconds(String date) {
		Integer seconds = null;
		if (date.split(" ").length < 2) {
			date += " 00:00:00";
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			seconds = Long.valueOf(format.parse(date).getTime() / 1000L).intValue();
		} catch (ParseException e) {
			seconds = Long.valueOf(System.currentTimeMillis()).intValue();
			e.printStackTrace();
		}
		return seconds;
	}

	/**
	 * 当天n天后时间转秒数
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Integer todayTimeToSecond(int n) {
		Calendar c = Calendar.getInstance();
		Integer seconds = null;
		String date = (c.getTime().getYear()+1900) + "-" + (c.getTime().getMonth() + 1) + "-" + (c.getTime().getDate() + n) + " 00:00:00";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {

			seconds = Long.valueOf(format.parse(date).getTime() / 1000L).intValue();
		} catch (ParseException e) {
			seconds = Long.valueOf(System.currentTimeMillis()).intValue();
			e.printStackTrace();
		}
		return seconds;
	}

	/**
	 * 将秒数转为时间格式为"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param seconds
	 * @return 
	 */
	public static String secondsToDateString(Integer seconds) {
		if(seconds==null){
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(seconds * 1000L);
		return ymdhms.format(c.getTime());
	}
	
	/**
	 * 将秒数转为时间格式为"yyyy-MM-dd"
	 * 
	 * @param seconds
	 * @return 
	 */
	public static String secondsToYmdDateString(Integer seconds) {
		if(seconds==null){
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(seconds * 1000L);
		return ymd.format(c.getTime());
	}
}