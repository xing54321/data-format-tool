package top.lbing.data_format_utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期与毫秒级时间戳转化工具类
 * @author xing54321
 *
 */
public class DateTimeUtils {
	
	// 日期格式
	/**
	 * 默认 yyyy-MM-dd HH:mm:ss
	 */
	static public String df1="yyyy-MM-dd HH:mm:ss";
	/**
	 * yyyy/MM/dd HH:mm:ss
	 */
	static public String df2="yyyy/MM/dd HH:mm:ss";
	/**
	 * yyyy年MM月dd日 HH时mm分ss秒
	 */
	static public String df3="yyyy年MM月dd日 HH时mm分ss秒";
	/**
	 * 默认 yyyy-MM-dd
	 */
	static public String df4="yyyy-MM-dd";
	/**
	 * yyyy/MM/dd
	 */
	static public String df5="yyyy/MM/dd";
	/**
	 * yyyy年MM月dd日
	 */
	static public String df6="yyyy年MM月dd日";
	
	/**
	 * yyyy-MM-dd HH:ss:mm.SSS
	 */
	static public String df7="yyyy-MM-dd HH:ss:mm.SSS";
	
	/**
	 * yyyy/MM/dd HH:ss:mm.SSS
	 */
	static public String df8="yyyy/MM/dd HH:ss:mm.SSS";
	
	/**
	 * Calendar.MILLISECOND,Calendar.SECOND,Calendar.MINUTE,Calendar.HOUR_OF_DAY,Calendar.DATE,Calendar.MONTH,Calendar.YEAR
	 */
	public final static int[] DATE_UNIT_ARR=new int[]{Calendar.MILLISECOND,Calendar.SECOND,Calendar.MINUTE,Calendar.HOUR_OF_DAY,Calendar.DATE,Calendar.MONTH,Calendar.YEAR};
	
	/**
	 * 13位毫秒级时间戳转化成日期和时间
	 * 
	 * @param timestamp
	 *          13位毫秒级时间戳
	 * @param df
	 *          日期格式，默认 yyyy-MM-dd HH:mm:ss
	 * @return String 日期
	 */
	public static String timestamp2DateTime(Long timestamp, String df) {
		if(isBlank(df)) {
			return new SimpleDateFormat(df1).format(timestamp);
		}
		return new SimpleDateFormat(df).format(timestamp);
	}
	
	/**
	 * 13位毫秒级时间戳转化成日期
	 * 
	 * @param timestamp
	 *          13位毫秒级时间戳
	 * @param df
	 *          日期格式，默认 yyyy-MM-dd
	 * @return String 日期
	 */
	public static String timestamp2Date(Long timestamp, String df) {
		if(isBlank(df)) {
			return new SimpleDateFormat(df4).format(timestamp);
		}
		return new SimpleDateFormat(df).format(timestamp);
	}
	
	/**
	 * 日期和时间转化成13位毫秒级时间戳
	 * 
	 * @param dateTime
	 *          日期和时间
	 * @param df
	 *          日期格式
	 * @return Long 13位毫秒级时间戳
	 * @throws ParseException
	 */
	public static Long dateTime2Timestamp(String dateTime, String df) throws ParseException {
		if(isBlank(df)) {
			return (new SimpleDateFormat(df1)).parse(dateTime).getTime();
		}
		return (new SimpleDateFormat(df)).parse(dateTime).getTime();
	}
	
	/**
	 * 日期转化成13位毫秒级时间戳
	 * 
	 * @param date
	 *          日期
	 * @param df
	 *          日期格式
	 * @return Long 13位毫秒级时间戳
	 * @throws ParseException
	 */
	public static Long date2Timestamp(String date, String df) throws ParseException {
		if(isBlank(df)) {
			return (new SimpleDateFormat(df4)).parse(date).getTime();
		}
		return (new SimpleDateFormat(df)).parse(date).getTime();
	}
	
	/**
	 * 将日期转为 字符串
	 * 
	 * @param date
	 * @param format
	 * @return String
	 */
	public static String dateToString(Date date, String format) {
		if(date==null) {
			return null;
		}
		return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * 将日期转换为 字符串(转换的时间按照当前登录用户的时区)
	 *
	 * @param date
	 * @param format
	 * @param timeZone
	 * @return String
	 */
	public static String dateToString(Date date, String format, String timeZone) {
		if(date==null) {
			return null;
		}
		// 1、格式化日期
		return getTimeZoneSimpleDateFormat(format,timeZone).format(date);
	}
	
	/**
	 * 获取当前登录用户的 日期格式化对象
	 *
	 * @param timeZone
	 * @param format
	 * @return SimpleDateFormat
	 */
	private static SimpleDateFormat getTimeZoneSimpleDateFormat(String format, String timeZone) {
		// 1、获取对应时区的格式化器
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		return simpleDateFormat;
	}
	
	/**
	 * 将字符串转为日期（转换的时间按照当前登录用户的时区）
	 * 
	 * @param dateStr
	 * @param format
	 * @return Date
	 * @throws ParseException
	 */
	public static Date stringToDate(String dateStr, String format, String timeZone) throws ParseException {
		if(dateStr!=null||format!=null) {
			return getTimeZoneSimpleDateFormat(format,timeZone).parse(dateStr);
		}
		return null;
	}
	
	/**
	 * 将字符串转为日期
	 * 
	 * @param dateStr
	 * @param format
	 *          默认 yyyy-MM-dd HH:mm:ss
	 * @return Date
	 * @throws ParseException
	 */
	public static Date stringToDate_CTT(String dateStr, String format) throws ParseException {
		if(dateStr==null) {
			return null;
		}
		if(format==null) {
			return getTimeZoneSimpleDateFormat(df1,"CTT").parse(dateStr);
		}
		return getTimeZoneSimpleDateFormat(format,"CTT").parse(dateStr);
	}
	
	/**
	 * 获取最近在当前日期之前的最后一个日期单位
	 *
	 * @param date
	 * @param calendarUnit
	 *          只支持 DateUtil.DATE_UNIT_ARR
	 * @return Date
	 */
	public static Date getFloorDate(Date date, int calendarUnit) {
		if(date==null) {
			return null;
		}
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		for(int i=0;i<=DATE_UNIT_ARR.length-1;i++) {
			if(DATE_UNIT_ARR[i]>calendarUnit) {
				if(Calendar.DATE==DATE_UNIT_ARR[i]) {
					calendar.set(DATE_UNIT_ARR[i],1);
				} else {
					calendar.set(DATE_UNIT_ARR[i],0);
				}
			}
			if(DATE_UNIT_ARR[i]==calendarUnit) {
				break;
			}
		}
		return calendar.getTime();
	}
	
	/**
	 * 获取最近在当前日期之后的第一个日期单位
	 *
	 * @param date
	 * @param calendarUnit
	 *          只支持 DATE_UNIT_ARR
	 * @return Date
	 */
	public static Date getCeilDate(Date date, int calendarUnit) {
		if(date==null) {
			return null;
		}
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		for(int i=0;i<=DATE_UNIT_ARR.length-1;i++) {
			if(DATE_UNIT_ARR[i]>calendarUnit) {
				if(Calendar.DATE==DATE_UNIT_ARR[i]) {
					calendar.set(DATE_UNIT_ARR[i],1);
				} else {
					calendar.set(DATE_UNIT_ARR[i],0);
				}
			}
			if(DATE_UNIT_ARR[i]==calendarUnit) {
				calendar.add(DATE_UNIT_ARR[i],1);
				break;
			}
		}
		return calendar.getTime();
	}
	
	/**
	 * 计算两个时间相差多少日期单位(不足一个日期单位的的按一个日期单位算)
	 *
	 * @param d1
	 *          开始时间
	 * @param d2
	 *          结束时间
	 * @return int 相差日期单位数
	 * @throws Exception
	 */
	public static int getDiff(Date d1, Date d2, int calendarUnit) throws Exception {
		double diff;
		switch(calendarUnit){
			case Calendar.DATE: {
				diff=1000*60*60*24;
				break;
			}
			case Calendar.HOUR_OF_DAY: {
				diff=1000*60*60;
				break;
			}
			case Calendar.MINUTE: {
				diff=1000*60;
				break;
			}
			case Calendar.SECOND: {
				diff=1000;
				break;
			}
			default: {
				throw new Exception("[DateUtil.getDiff],Calendar Unit Not Support!");
			}
		}
		Long begin=d1.getTime();
		Long end=d2.getTime();
		Double res=(end-begin)/diff;
		return (int)Math.ceil(res);
	}
	
	/**
	 * 获取一个日期的数字表示形式 例如: 2018-3-12 15:13:12 888 表示成 20180312151312888
	 *
	 * @param date
	 * @param calendarUnit
	 *          最小的日期单位
	 * @return
	 */
	// public static Long getDateNum(Date date, int calendarUnit) {
	// if(date==null) {
	// return null;
	// }
	// StringBuffer sb=new StringBuffer();
	// Calendar c=Calendar.getInstance();
	// c.setTime(date);
	// if(calendarUnit>=Calendar.YEAR) {
	// sb.append(c.get(Calendar.YEAR));
	// }
	// if(calendarUnit>=Calendar.MONTH) {
	// sb.append(FormatUtil.formatToString(c.get(Calendar.MONTH)+1,"00"));
	// }
	// if(calendarUnit>=Calendar.DATE) {
	// sb.append(FormatUtil.formatToString(c.get(Calendar.DATE),"00"));
	// }
	// if(calendarUnit>=Calendar.HOUR_OF_DAY) {
	// sb.append(FormatUtil.formatToString(c.get(Calendar.HOUR_OF_DAY),"00"));
	// }
	// if(calendarUnit>=Calendar.MINUTE) {
	// sb.append(FormatUtil.formatToString(c.get(Calendar.MINUTE),"00"));
	// }
	// if(calendarUnit>=Calendar.SECOND) {
	// sb.append(FormatUtil.formatToString(c.get(Calendar.SECOND),"00"));
	// }
	// if(calendarUnit>=Calendar.MILLISECOND) {
	// sb.append(FormatUtil.formatToString(c.get(Calendar.MILLISECOND),"000"));
	// }
	// return Long.parseLong(sb.toString());
	// }
	
	/**
	 * 判断两个日期是否相等
	 *
	 * @param d1
	 * @param d2
	 * @param calendarUnit
	 *          对比的最小日期单位
	 * @return boolean
	 */
	public static boolean isEqual(Date d1, Date d2, int calendarUnit) {
		Calendar c1=Calendar.getInstance();
		Calendar c2=Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		for(int i=DATE_UNIT_ARR.length-1;i>=0;i--) {
			if(calendarUnit>=DATE_UNIT_ARR[i]) {
				int v1=c1.get(DATE_UNIT_ARR[i]);
				int v2=c2.get(DATE_UNIT_ARR[i]);
				if(v1!=v2) {
					return false;
				}
			} else {
				break;
			}
		}
		return true;
	}
	
	/**
	 * 获取当天初始时间
	 *
	 * @param date
	 *          时间
	 * @return Date 初始时间 (yyyy-MM-dd 00:00:00)
	 * @throws ParseException
	 */
	public static Date getInitialTime(Date date) throws ParseException {
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String dateStr=dateFormat.format(date);
		return dateFormat.parse(dateStr);
	}
	
	/**
	 * 获取当天最后时间
	 *
	 * @param date
	 *          时间
	 * @return Date 最后时间 (yyyy-MM-dd 23:59:59)
	 * @throws ParseException
	 */
	public static Date getTerminalTime(Date date) throws ParseException {
		
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String dateStr=dateFormat.format(date);
		dateStr=dateStr+" 23:59:59";
		dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.parse(dateStr);
	}
	
	/**
	 * 计算两个时间间隔多少秒
	 *
	 * @param startDate
	 * @param endDate
	 * @return Date
	 */
	public static Long intervalTime(Date startDate, Date endDate) {
		long a=endDate.getTime();
		long b=startDate.getTime();
		Long c=((a-b)/1000);
		return c;
	}
	
	/**
	 * 检测一个日期是否在 时分秒 之内,startHms<time<endHms,支持跨天的小时
	 *
	 * @param time
	 * @param startHms
	 * @param endHms
	 * @return boolean
	 */
	public static boolean checkHmsIn(Date time, String startHms, String endHms) {
		if(startHms==null||endHms==null||time==null) {
			return true;
		}
		LocalTime startTime=LocalTime.of(Integer.valueOf(startHms.substring(0,2)),Integer.valueOf(startHms.substring(2,4)),Integer.valueOf(startHms.substring(4,6)));
		LocalTime endTime=LocalTime.of(Integer.valueOf(endHms.substring(0,2)),Integer.valueOf(endHms.substring(2,4)),Integer.valueOf(endHms.substring(4,6)));
		LocalTime curTime=LocalDateTime.ofInstant(time.toInstant(),ZoneId.of("+8")).toLocalTime();
		
		if(endTime.isAfter(startTime)) {
			return startTime.isBefore(curTime)&&endTime.isAfter(curTime);
		} else {
			return (startTime.isBefore(curTime)&&LocalTime.MAX.isAfter(curTime))||(LocalTime.MIN.isBefore(curTime)&&endTime.isAfter(curTime));
		}
	}
	
	/**
	 * 将字符串转化为日期
	 * 
	 * @param date
	 *          日期
	 * @param df
	 *          字符串格式，默认 Sat Mar 23 00:00:00 GMT+08:00
	 * @return Date 日期，格式 Sat Mar 23 00:00:00 GMT+08:00
	 * @throws ParseException
	 */
	public static Date stringParseDate(String date, String df) throws ParseException {
		SimpleDateFormat formatter=null;
		if(isBlank(df)) {
			formatter=new SimpleDateFormat(df1);
		} else {
			formatter=new SimpleDateFormat(df);
		}
		Date parseDate=formatter.parse(date);
		return parseDate;
	}
	
	/**
	 * 格式化日期 例子：parseDate("2019/11/10","yyyy-MM-dd HH:mm:ss") 返回 2019-11-10 00:00:00
	 * 
	 * @param dateStr
	 *          字符型日期
	 * @param format
	 *          格式
	 * @return Date 日期
	 * @throws ParseException
	 */
	public static Date parseDate(String dateStr, String format) throws ParseException {
		DateFormat dateFormat=null;
		Date date=null;
		dateFormat=new SimpleDateFormat(format);
		// 注意(Date)dateFormat.parse(dt)只能转"yyyy-MM-dd HH:mm:ss"
		String dt=dateStr.replaceAll("/","-");
		if((!dt.equals(""))&&(dt.length()<format.length())) {
			dt+=format.substring(dt.length()).replaceAll("[YyMmDdHhSs]","0");
		}
		date=(Date)dateFormat.parse(dt);
		return date;
	}
	
	/**
	 * 天数转化为毫秒数
	 * 
	 * @param day
	 * @return Long
	 */
	public static Long toMilis(Integer day) {
		if(null!=day) {
			return (long)(day*24*3600*1000);
		}
		return (long)0;
	}
	
	/**
	 * 判断日期格式： <br/>
	 * df1: yyyy-MM-dd HH:mm:ss <br/>
	 * df2: yyyy/MM/dd HH:mm:ss <br/>
	 * df3: yyyy年MM月dd日 HH时mm分ss秒 <br/>
	 * df4: yyyy-MM-dd <br/>
	 * df5: yyyy/MM/dd <br/>
	 * df6: yyyy年MM月dd日 <br/>
	 * df7: yyyy-MM-dd HH:ss:mm.SSS <br/>
	 * df8: yyyy/MM/dd HH:ss:mm.SSS <br/>
	 * 
	 * @param timeStr
	 * @return
	 */
	public static String getDateFormat(String timeStr) {
		String regex1="\\d{4}-\\d{2}-\\d{2}\\s{1}\\d{2}:\\d{2}:\\d{2}";
		String regex2="\\d{4}/\\d{2}/\\d{2}\\s{1}\\d{2}:\\d{2}:\\d{2}";
		String regex3="\\d{4}年\\d{2}月\\d{2}日\\s{1}\\d{2}时\\d{2}分\\d{2}秒";
		String regex4="\\d{4}-\\d{2}-\\d{2}";
		String regex5="\\d{4}/\\d{2}/\\d{2}";
		String regex6="\\d{4}年\\d{2}月\\d{2}日";
		String regex7="\\d{4}-\\d{2}-\\d{2}\\s{1}\\d{2}:\\d{2}:\\d{2}.\\d{3}";
		String regex8="\\d{4}/\\d{2}/\\d{2}\\s{1}\\d{2}:\\d{2}:\\d{2}.\\d{3}";
		
		// 编译正则表达式
		Pattern pattern1=Pattern.compile(regex1);
		Pattern pattern2=Pattern.compile(regex2);
		Pattern pattern3=Pattern.compile(regex3);
		Pattern pattern4=Pattern.compile(regex4);
		Pattern pattern5=Pattern.compile(regex5);
		Pattern pattern6=Pattern.compile(regex6);
		Pattern pattern7=Pattern.compile(regex7);
		Pattern pattern8=Pattern.compile(regex8);
		
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher1=pattern1.matcher(timeStr);
		Matcher matcher2=pattern2.matcher(timeStr);
		Matcher matcher3=pattern3.matcher(timeStr);
		Matcher matcher4=pattern4.matcher(timeStr);
		Matcher matcher5=pattern5.matcher(timeStr);
		Matcher matcher6=pattern6.matcher(timeStr);
		Matcher matcher7=pattern7.matcher(timeStr);
		Matcher matcher8=pattern8.matcher(timeStr);
		
		if(matcher1.matches()) {
			return df1;
		}
		if(matcher2.matches()) {
			return df2;
		}
		if(matcher3.matches()) {
			return df3;
		}
		if(matcher4.matches()) {
			return df4;
		}
		if(matcher5.matches()) {
			return df5;
		}
		if(matcher6.matches()) {
			return df6;
		}
		if(matcher7.matches()) {
			return df7;
		}
		if(matcher8.matches()) {
			return df8;
		}
		return null;
	}
	
	/**
   * check string is null or blank or blank after trim
   * 
   * @param source
   * @return
   */
  public static final boolean isBlank(String source) {
    return (source == null || "".equals(source.trim()));
  }
	
	public static void main(String[] args) {
		// System.out.println(date2Timestamp("2019/09/30 15:57:33",DateTimeUtils.df2));
		
		// System.out.println(DateTimeUtils.timestamp2Date(1569830253000l,DateTimeUtils.df1));
		// System.out.println(DateTimeUtils.timestamp2Date(1569830253000l,DateTimeUtils.df2));
		// System.out.println(DateTimeUtils.timestamp2Date(1569830253000l,DateTimeUtils.df3));
		// System.out.println(DateTimeUtils.timestamp2Date(1569830253000l,DateTimeUtils.df4));
		// System.out.println(DateTimeUtils.timestamp2Date(1569830253000l,DateTimeUtils.df5));
		// System.out.println(DateTimeUtils.timestamp2Date(1569830253000l,DateTimeUtils.df6));
		
		// 获取当前地区时区
		// TimeZone timeZone=Calendar.getInstance().getTimeZone();
		// System.out.println(timeZone.getID());
		// System.out.println(timeZone.getDisplayName());
		
		// try {
		// System.out.println(DateTimeUtils
		// .getCeilDate(DateTimeUtils.stringToDate("2019-10-18
		// 11:17:01",df1,timeZone.getID()),Calendar.MINUTE));
		// } catch(ParseException e) {
		// e.printStackTrace();
		// }
		
		// 检验一个日期是否在规定日期之间
		// try {
		// System.out.println(DateTimeUtils.checkHmsIn(
		// DateTimeUtils.stringToDate("2019-10-18
		// 11:17:01",DateTimeUtils.df1,"Asia/Shanghai"),"111700","111702"));
		// System.out.println(parseDate("2019/03/01","yyyy-MM-dd
		// HH:mm:ss","/","-").toString());
		// } catch(Exception e) {
		// e.printStackTrace();
		// }
		
		System.out.println("时区："+System.getProperty("user.timezone"));
		System.out.println("时区："+System.getProperty("user.timezone"));
	}
	
}
