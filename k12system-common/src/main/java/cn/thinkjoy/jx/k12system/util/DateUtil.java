package cn.thinkjoy.jx.k12system.util;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 * 
 * @author fly
 * 
 */
public class DateUtil {
	/**
	 * yyyy-MM-dd HH:mm to  long
	 * @param stringDate
	 * @param regex
	 * @return
	 */
	public static long StringDateToLong(String stringDate,String regex){
		if(StringUtils.isBlank(stringDate)||StringUtils.isBlank(regex)){
			return 0l;
		}
		else {
			Date date = str2date(stringDate,regex);
			long longDate = date2long(date);
			return longDate;
		}
	}
	public static long date2long(Date date){
		if(date == null){
			return 0l;
		}else {
			long longDate = date.getTime();
			return longDate;
		}
	}
	/**
	 * 根据type将long类型转换为时间
	 *
	 * @param dateLong
	 * @param type 0:yyyy年MM月dd日 HH:mm:ss
	 * 			   1:MM月dd日 HH:mm
	 * 			   2:yyyy/MM/dd HH:mm
	 * @return
	 */
	public static String longToDateString(Long dateLong,int type){
		SimpleDateFormat sdf = null;
		if(type == 0){
			//sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
		}else if(type == 1){
			sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm",Locale.CHINA);
		}else if (type == 2) {
			sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm",Locale.CHINA);
		}else if(type == 3){
			sdf = new SimpleDateFormat("yyyy-MM-dd" ,Locale.CHINA);
		}else if(type == 4){
			sdf = new SimpleDateFormat("HH:mm" ,Locale.CHINA);
		}else if(type == 5){
			sdf = new SimpleDateFormat("EEEE", Locale.CHINA);
		}
		//前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
		Date dt = new Date(dateLong);
		String sDateTime = sdf.format(dt);  //得到精确到秒的表示
		return sDateTime;
	}


	public static String getTimeStamptoEn() {
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		return simple.format(new Date());
	}

	public static String getTimeStamptoCH() {
		SimpleDateFormat simple = new SimpleDateFormat("MM月dd日 HH:mm");
		return simple.format(new Date());
	}

	/**
	 * 取当前时间格式"yyyyMMddHHmmssSSSS"
	 * 
	 * @return
	 */
	public static String getTimeStamp() {
		return date2str(new Date(), "yyyyMMddHHmmssSSS");
	}

	/**
	 * 取当前时间格式"yyyyMMddHHmmss"
	 * 
	 * @return
	 */
	public static String getNowTimeStamp() {
		return date2str(new Date(), "yyyyMMddHHmmss");
	}

	/**
	 * 从regex1格式的date字符串转为regex2格式的date字符串
	 * 
	 * @param date
	 * @param regex1
	 * @param regex2
	 * @return
	 */
	public static String dateFormateChange(String date, String regex1,
			String regex2) {
		Date d = null;
		try {
			d = new SimpleDateFormat(regex1).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new SimpleDateFormat(regex2).format(d);

	}

	/**
	 * 把格式为regex的字符串转为date类型的日期
	 * 
	 * @param date
	 * @param regex
	 * @return
	 */
	public static Date str2date(String date, String regex) {
		if (StringUtils.isEmpty(date)) {
			return null;
		}
		Date d = null;
		try {
			d = new SimpleDateFormat(regex).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * 把date类型的日期转为格式为regex的字符串
	 * 
	 * @param date
	 * @param regex
	 * @return
	 */
	public static String date2str(Date date, String regex) {
		SimpleDateFormat simple = new SimpleDateFormat(regex);
		return simple.format(date);
	}

	/**
	 * 日期转化为大小写(吉林大学珠海学院文件专用)
	 * 
	 * @param date
	 * @return
	 */
	public static String dataToUpper(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		int year = ca.get(Calendar.YEAR);
		int month = ca.get(Calendar.MONTH) + 1;
		int day = ca.get(Calendar.DAY_OF_MONTH);
		return numToUpper(year) + "年" + monthToUppder(month) + "月"
				+ dayToUppder(day) + "日";
	}

	/**
	 * 将数字转化为大写
	 * 
	 * @param num
	 * @return
	 */
	public static String numToUpper(int num) {
		String u[] = { "〇", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		char[] str = String.valueOf(num).toCharArray();
		String rstr = "";
		for (int i = 0; i < str.length; i++) {
			rstr = rstr + u[Integer.parseInt(str[i] + "")];
		}
		return rstr;
	}

	/**
	 * 月转化为大写
	 * 
	 * @param month
	 * @return
	 */
	public static String monthToUppder(int month) {
		if (month < 10) {
			return numToUpper(month);
		} else if (month == 10) {
			return "十";
		} else {
			return "十" + numToUpper(month - 10);
		}
	}

	/**
	 * 日转化为大写
	 * 
	 * @param day
	 * @return
	 */
	public static String dayToUppder(int day) {
		if (day < 20) {
			return monthToUppder(day);
		} else {
			char[] str = String.valueOf(day).toCharArray();
			// 三元运算
			return (str[1] == '0') ? numToUpper(Integer.parseInt(str[0] + ""))
					+ "十" : numToUpper(Integer.parseInt(str[0] + "")) + "十"
					+ numToUpper(Integer.parseInt(str[1] + ""));
		}
	}

	/**
	 * 
	 * 获得一个月有几周,-1为默认当前
	 * 
	 */
	public static int weeksInMonth(int year, int month) {

		Calendar calendar = Calendar.getInstance();

		calendar.setFirstDayOfWeek(Calendar.MONDAY);

		if (-1 == year) {
			year = calendar.get(Calendar.YEAR);
		}

		if (-1 == month) {
			month = calendar.get(Calendar.MONTH) + 1;
		}

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);

		return calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);

	}

	/**
	 * 
	 * 获得某年，某月，第n周的开始时间和结束时间，用“，”分割 -1为默认当前
	 * 
	 */
	public static String dateWeekOfMonth(int year, int month, int weeks) {

		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");

		if (-1 == year) {
			year = calendar.get(Calendar.YEAR);
		}

		if (-1 == month) {
			month = calendar.get(Calendar.MONTH) + 1;
		}
		if (-1 == weeks) {
			weeks = calendar.get(Calendar.WEEK_OF_MONTH);
		}

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.WEEK_OF_MONTH, weeks);

		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		String weekBegin = formatter.format(calendar.getTime());

		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

		String weekEnd = formatter.format(calendar.getTime());

		return weekBegin + "," + weekEnd;
	}

	/**
	 * 得到一天的开始时间以及结束时间,用“，”分割 -1为默认当前
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param format
	 *            时间格式 默认yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getStartAndEndtimeOfDay(int year, int month, int day,
			String format) {
		Calendar calendar = Calendar.getInstance();
		if (StringUtils.isBlank(format)) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);

		if (-1 == year) {
			year = calendar.get(Calendar.YEAR);
		}

		if (-1 == month) {
			month = calendar.get(Calendar.MONTH) + 1;
		}
		if (-1 == day) {
			day = calendar.get(Calendar.DAY_OF_MONTH);
		}
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		initStartTimeOfDay(calendar);
		String startTime = formatter.format(calendar.getTime());

		initEndTimeOfDay(calendar);
		String endTime = formatter.format(calendar.getTime());
		return startTime + "," + endTime;
	}

	/**
	 * 得到一周的开始时间以及结束时间,用“，”分割 -1为默认当前
	 * 
	 * @param year
	 * @param month
	 * @param weeks
	 * @param format
	 *            时间格式 默认yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getStartAndEndtimeOfWeek(int year, int month,
			int weeks, String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		if (StringUtils.isBlank(format)) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);

		if (-1 == year) {
			year = calendar.get(Calendar.YEAR);
		}

		if (-1 == month) {
			month = calendar.get(Calendar.MONTH) + 1;
		}
		if (-1 == weeks) {
			weeks = calendar.get(Calendar.WEEK_OF_MONTH);
		}

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.WEEK_OF_MONTH, weeks);

		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		initStartTimeOfDay(calendar);
		String weekBegin = formatter.format(calendar.getTime());

		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

		initEndTimeOfDay(calendar);
		String weekEnd = formatter.format(calendar.getTime());

		return weekBegin + "," + weekEnd;
	}

	/**
	 * 得到一个月的开始时间以及结束时间,用“，”分割 -1为默认当前
	 * 
	 * @param year
	 * @param month
	 * @param format
	 *            时间格式 默认yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getStartAndEndtimeOfMonth(int year, int month,
			String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		if (StringUtils.isBlank(format)) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);

		if (-1 == year) {
			year = calendar.get(Calendar.YEAR);
		}

		if (-1 == month) {
			month = calendar.get(Calendar.MONTH) + 1;
		}
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		initStartTimeOfDay(calendar);
		String monthBegin = formatter.format(calendar.getTime());

		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		initEndTimeOfDay(calendar);
		String monthEnd = formatter.format(calendar.getTime());
		return monthBegin + "," + monthEnd;
	}

	/**
	 * 得到一年的开始时间以及结束时间,用“，”分割 -1为默认当前
	 * 
	 * @param year
	 * @param format
	 *            时间格式 默认yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getStartAndEndtimeOfYear(int year, String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		if (StringUtils.isBlank(format)) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);

		if (-1 == year) {
			year = calendar.get(Calendar.YEAR);
		}
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		initStartTimeOfDay(calendar);
		String yearBegin = formatter.format(calendar.getTime());

		calendar.set(Calendar.MONTH, calendar.getActualMaximum(Calendar.MONTH));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		initEndTimeOfDay(calendar);
		String yearEnd = formatter.format(calendar.getTime());
		return yearBegin + "," + yearEnd;
	}

	public static void initStartTimeOfDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
	}

	public static void initEndTimeOfDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
	}

	/**
	 * 获取几个月之后的日期
	 * 
	 * @param startDate
	 * @param month
	 * @return
	 */
	public static Date getDateAfterMonth(Date startDate, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + month);
		return calendar.getTime();
	}

	/**
	 * 获得某月，时间格式：YYYY年MM月,-1为默认当前
	 */

	public static String getMonthDate(int year, int month) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月");

		Calendar calendar = Calendar.getInstance();

		if (-1 == year) {
			year = calendar.get(Calendar.YEAR);
		}

		if (-1 == month) {
			month = calendar.get(Calendar.MONTH) + 1;
		}

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);

		return dateFormat.format(calendar.getTime());

	}

	/**
	 * 获得某年，某月的第n周是一年中的第几周,-1为默认当前
	 * 
	 */
	public static int weeksInYear(int year, int month, int weeksInMonth) {

		Calendar calendar = Calendar.getInstance();

		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		if (-1 == year) {
			year = calendar.get(Calendar.YEAR);
		}
		if (-1 == month) {
			month = calendar.get(Calendar.MONTH) + 1;
		}
		if (-1 == weeksInMonth) {
			weeksInMonth = calendar.get(Calendar.WEEK_OF_MONTH);
		}

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.WEEK_OF_MONTH, weeksInMonth);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 
	 * 根据某年、某月、第n周获得本周的最后一天。-1为默认当前
	 * 
	 * 
	 */
	public static Date getLastDayOfWeek(int year, int month, int weeksInMonth) {

		Calendar calendar = Calendar.getInstance();
		// 设置星期一为每周的第一天
		calendar.setFirstDayOfWeek(Calendar.MONDAY);

		if (-1 == year) {
			year = calendar.get(Calendar.YEAR);
		}
		if (-1 == month) {
			month = calendar.get(Calendar.MONTH) + 1;
		}
		if (-1 == weeksInMonth) {
			weeksInMonth = calendar.get(Calendar.WEEK_OF_MONTH);
		}
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.WEEK_OF_MONTH, weeksInMonth);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

		return calendar.getTime();
	}

	/**
	 * 获得当前年
	 */
	public static int getYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 获得当前月
	 */
	public static int getMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * 获得当前天
	 */
	public static int getDay() {
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}



	/**
	 * 获得某月的最后一天
	 */
	public static Date getLastDayOfMonth(int year, int month) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DATE));
		return calendar.getTime();
	}

	/**
	 * 获得当前周是当前月的第几周
	 */
	public static int getWeekOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);// 设置周一为一周的第一天
		return calendar.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 比较两个时间,如果d1在d2之前则返回1，之后则返回-1，相等返回0
	 */
	public static int compareDate(Date d1, Date d2) {

		if (d1.before(d2)) {
			return 1;
		} else if (d1.after(d2)) {
			return -1;
		} else {
			return 0;
		}
	}

	public static String getToday(String pattern) {
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		return formatDate(today, pattern);
	}

	public static String formatDate(Date date, String pattern) {
		if (null == pattern || "".equals(pattern)) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	/**
	 * 判断两个日期相差的天数,前面的日期大于后面的
	 */
	public static long daysBetween(Date date1, Date date2) {

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(date1);
		c2.setTime(date2);

		long milliseconds1 = c1.getTimeInMillis();
		long milliseconds2 = c2.getTimeInMillis();

		long diff = milliseconds1 - milliseconds2;

		long days = diff / (24 * 60 * 60 * 1000);

		return days;
	}

	/**
	 * 
	 * 判断两个日期相差的月数,前面的日期小于后面的日期
	 */

	public static int monthBetween(Date date1, Date date2) {

		int result = 0;

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(date1);
		c2.setTime(date2);

		if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) {
			result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
		} else {
			result = 12 * (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR))
					+ c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
		}
		return result == 0 ? 1 : Math.abs(result);
	}

	/**
	 * 得到N个月后的时间元素
	 */
	public static Calendar calendarAfterNWeek(Calendar calendar, int n) {

		calendar.set(Calendar.WEEK_OF_YEAR, calendar.get(Calendar.WEEK_OF_YEAR)
				- n);
		return calendar;
	}

	/**
	 * 
	 * 判断两个日期相差的年数,前面的日期大于后面的
	 */

	public static long yearBetween(Date date1, Date date2) {
		long days = daysBetween(date1, date2);
		return days / 365;
	}

	/**
	 * 判断两个日期是否为同一个月
	 * 
	 * @param date1
	 * @param date2
	 * @return true 为同一个月 false 不为同一个月
	 */
	public static boolean isSameMonth(Date date1, Date date2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);
		int year1 = calendar1.get(Calendar.YEAR);
		int year2 = calendar2.get(Calendar.YEAR);
		int month1 = calendar1.get(Calendar.MONTH);
		int month2 = calendar2.get(Calendar.MONTH);
		if (year1 == year2 && month1 == month2) {
			return true;
		}
		return false;
	}

	/**
	 * 根据type将long类型转换为时间
	 * 
	 * @param dateLong
	 * @param type
	 *            0:yyyy年MM月dd日 HH:mm:ss 1:MM月dd日 HH:mm 2:yyyy/MM/dd HH:mm
	 * @return
	 */
	public static String longToDate(Long dateLong, int type) {
		SimpleDateFormat sdf = null;
		if (type == 0) {
			sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		} else if (type == 1) {
			sdf = new SimpleDateFormat("MM月dd日 HH:mm");
		} else if (type == 2) {
			sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		} else if(type == 3){
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		}
		// 前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
		Date dt = new Date(dateLong);
		String sDateTime = sdf.format(dt); // 得到精确到秒的表示
		return sDateTime;
	}

	/**
	 * 当前时间向后推24小时
	 * @param hours
	 * @return
	 */
	public static long nowTimeAddHours(int hours){
		long currentTime = System.currentTimeMillis() + hours * 60 * 60 * 1000;
		Date date = new Date(currentTime);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime="";
		nowTime= df.format(date);
		System.out.println(nowTime);
		return 0;
	}
	public static void main(String[] args) {

		System.out.println(longToDateString(1500000000l*1000l,0));

	}
}