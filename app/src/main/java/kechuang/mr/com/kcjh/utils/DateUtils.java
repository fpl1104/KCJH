package kechuang.mr.com.kcjh.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	/** 格式：年－月－日 小时：分钟：秒 */
	public static final String FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";
	/** 格式：年月日小时分钟秒 */
	public static final String FORMAT_TIME_LONG = "yyyyMMddHHmmss";
	/** 格式：年－月－日 */
	public static final String LONG_DATE_FORMAT = "yyyy-MM-dd";

	@SuppressLint("SimpleDateFormat")
	public static String formatDateStr(String fromformat, String toformat, String sourcestr) {
		SimpleDateFormat fromDateFormat = new SimpleDateFormat(fromformat);
		Date date;
		try {
			date = fromDateFormat.parse(sourcestr);
			SimpleDateFormat toDateFormat = new SimpleDateFormat(toformat);
			return toDateFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 得到当前 dt 是星期几。
	 * 
	 * @param dt
	 * @return
	 */
	@SuppressLint("NewApi")
	public static String getWeekOfDate(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		return DateFormatSymbols.getInstance().getShortWeekdays()[cal.get(Calendar.DAY_OF_WEEK)];
	}

	/**
	 * 得到当前yyyyMMdd是星期几。
	 * 
	 * @param dt
	 * @return
	 */
	@SuppressLint("NewApi")
	public static String getWeekOfDate(String time) {
		Date dt = stringtoDate(time, "yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		return DateFormatSymbols.getInstance().getShortWeekdays()[cal.get(Calendar.DAY_OF_WEEK)];
	}

	/**
	 * 是否是今天 Calendar
	 * 
	 * @param queryCal
	 * @return
	 */
	public static boolean isToday(final Calendar queryCal) {
		return isSameDay(Calendar.getInstance(), queryCal);
	}

	/**
	 * 是否是今天 Date
	 * 
	 * @param queryCal
	 * @return
	 */
	public static boolean isToday(final Date queryDate) {
		final Calendar queryCal = Calendar.getInstance();
		queryCal.setTime(queryDate);
		return isToday(queryCal);
	}

	/**
	 * 是否是今天 Date yyyymmdd
	 * 
	 * @param time
	 * @return
	 */
	public static boolean isToday(final String time) {
		Date queryDate = stringtoDate(time, "yyyyMMdd");
		final Calendar queryCal = Calendar.getInstance();
		queryCal.setTime(queryDate);
		return isToday(queryCal);
	}

	/**
	 * 比较日期是否相同 Calendar
	 * 
	 * @param firstCal
	 * @param secondCal
	 * @return
	 */
	public static boolean isSameDay(final Calendar firstCal, final Calendar secondCal) {
		return (firstCal.get(Calendar.ERA) == secondCal.get(Calendar.ERA) && firstCal.get(Calendar.YEAR) == secondCal.get(Calendar.YEAR) && firstCal
				.get(Calendar.DAY_OF_YEAR) == secondCal.get(Calendar.DAY_OF_YEAR));
	}

	/**
	 * 比较日期是否相同 Date
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @return
	 */
	public static boolean isSameDay(final Date firstDate, final Date secondDate) {
		final Calendar firstCal = Calendar.getInstance();
		final Calendar secondCal = Calendar.getInstance();
		firstCal.setTime(firstDate);
		secondCal.setTime(secondDate);
		return isSameDay(firstCal, secondCal);
	}

	/**
	 * 是否是昨天 Calendar
	 * 
	 * @param queryCal
	 * @return
	 */
	public static boolean isYesterday(final Calendar queryCal) {
		return isSameDay(getDayBefore(Calendar.getInstance()), queryCal);
	}

	/**
	 * 是否是昨天 Date
	 * 
	 * @param queryCal
	 * @return
	 */
	public static boolean isYesterday(final Date queryDate) {
		final Calendar queryCal = Calendar.getInstance();
		queryCal.setTime(queryDate);
		return isYesterday(queryCal);
	}

	/**
	 * 是否是昨天 Date yyyymmdd
	 * 
	 * @param queryCal
	 * @return
	 */
	public static boolean isYesterday(final String dateStr) {
		final Calendar queryCal = Calendar.getInstance();
		Date queryDate = stringtoDate(dateStr, "yyyyMMdd");
		queryCal.setTime(queryDate);
		return isYesterday(queryCal);
	}

	/**
	 * 得到前一天日期
	 * 
	 * @param target
	 * @return
	 */
	public static Calendar getDayBefore(final Calendar target) {

		target.add(Calendar.DAY_OF_YEAR, -1);
		return target;
	}

	/**
	 * 把符合日期格式的字符串转换为日期类型
	 * 
	 * @param dateStr
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date stringtoDate(String dateStr, String format) {
		Date d = null;
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			formater.setLenient(false);
			d = formater.parse(dateStr);
		} catch (Exception e) {
			d = null;
		}
		return d;
	}

	/**
	 * 获得当前月份
	 * 
	 * @return int
	 */
	public static int getToMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 根据输入出身年月日得到年龄
	 * 
	 * @param data
	 * @return
	 */
	public static int getAge(String data) {
		if (TextUtils.isEmpty(data))
			return 0;
		Date now = new Date();
		String thisYearMonth = new SimpleDateFormat("yyyyMMdd").format(now);
		int age = (Integer.parseInt(thisYearMonth) - Integer.parseInt(data)) / 10000;
		return age;

	}

}
